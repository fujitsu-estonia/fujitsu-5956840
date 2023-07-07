package ee.fujitsu.smit.hotel.tools.validation;

import ee.fujitsu.smit.hotel.models.PersonData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Constraint Validator for id codes
 */
public class PersonIdentityValidator implements ConstraintValidator<ValidIdentity, PersonData> {

  private static final Map<ValidIdentity.IdCodeValidationRuleSet, Predicate<String>> VALIDATORS =
      Map.of(
          ValidIdentity.IdCodeValidationRuleSet.ESTONIAN_NATIONAL_ID,
          new EstonianNationalIdValidator(),
          ValidIdentity.IdCodeValidationRuleSet.OTHER,
          s -> false);

  private List<ValidIdentity.IdCodeValidationRuleSet> idCodeValidationRuleSets;

  @Override
  public void initialize(ValidIdentity constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    idCodeValidationRuleSets = Arrays.asList(constraintAnnotation.idCodeValidationRuleSets());
  }

  @Override
  public boolean isValid(PersonData personData, ConstraintValidatorContext context) {
    if (personData.isIgnoreIdCode()) {
      return true;
    }
    if (idCodeValidationRuleSets.stream()
        .map(VALIDATORS::get)
        .filter(Objects::nonNull)
        .anyMatch(validator -> validator.test(personData.getIdCode()))) {
      return true;
    }
    String msgTpl = context.getDefaultConstraintMessageTemplate();
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(msgTpl).addConstraintViolation();
    return false;
  }

  /**
   * Estonian ID code validation logic. Can be moved to own file, but due to the lack of other
   * validation rule sets, decided to keep it here
   *
   * <p><a href="https://gist.github.com/tuupola/180321">original code</a>
   */
  private static class EstonianNationalIdValidator implements Predicate<String> {
    private static final int[] MULTIPLIER_1 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    private static final int[] MULTIPLIER_2 = new int[] {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};

    /**
     * Run Estonian ID code validation
     *
     * @param code code
     * @return {@code true} if code is valid Estonian ID code
     */
    @Override
    public boolean test(String code) {
      if (code == null || code.length() > 11) {
        return false;
      }

      var control = getDigit(code, 10);

      var mod = 0;
      var total = 0;

      /* Do first run. */
      for (var i = 0; i < 10; i++) {
        total += getDigit(code, i) * MULTIPLIER_1[i];
      }
      mod = total % 11;

      /* If modulus is ten we need second run. */
      total = 0;
      if (10 == mod) {
        for (var i = 0; i < 10; i++) {
          total += getDigit(code, i) * MULTIPLIER_2[i];
        }
        mod = total % 11;

        /* If modulus is still ten revert to 0. */
        if (10 == mod) {
          mod = 0;
        }
      }

      return control == mod;
    }

    private int getDigit(String code, int pos) {
      return Character.getNumericValue(code.charAt(pos));
    }
  }
}
