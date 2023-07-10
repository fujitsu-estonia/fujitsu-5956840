package ee.fujitsu.smit.hotel.tools.validation;

import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import ee.fujitsu.smit.hotel.tools.validation.ValidIdentity.IdCodeValidationRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

class PersonIdentityValidatorTest extends ConstraintValidatorTest {

  @BeforeEach
  void mockDefaultConstraintMessageTemplate() {
    lenient()
        .doReturn(Constants.ERROR_ID_CODE_INVALID)
        .when(validationContext)
        .getDefaultConstraintMessageTemplate();
  }

  @Test
  void test_whenIgnoreIdCodeFlagIsSet_thenIsValidWithAnyIdCode() {
    var validator = new PersonIdentityValidator();
    validator.initialize(mockConstraintAnnotation(IdCodeValidationRuleSet.OTHER));

    var personData = PersonData.builder().ignoreIdCode(true);

    var isValid = validator.isValid(personData.idCode(null).build(), validationContext);

    assertTrue(isValid);

    isValid = validator.isValid(personData.idCode("50001010017").build(), validationContext);

    assertTrue(isValid);

    isValid = validator.isValid(personData.idCode("random_id_code").build(), validationContext);

    assertTrue(isValid);
  }

  @ParameterizedTest
  @CsvSource(
      value = {
        ",false",
        "shortCode,false",
        "randomLongCode,false",
        "50001010016,false",
        "50001010017,true",
        "33706284279,true",
        "60908242749,true",
        "48704230028,true"
      })
  void test_estonianNationalIdRuleSetValidation(String testIdCode, boolean expectedIsValid) {
    var validator = new PersonIdentityValidator();
    validator.initialize(mockConstraintAnnotation(IdCodeValidationRuleSet.ESTONIAN_NATIONAL_ID));

    var personData = PersonData.builder().ignoreIdCode(false).idCode(testIdCode).build();

    var isValid = validator.isValid(personData, validationContext);

    assertEquals(expectedIsValid, isValid);
  }

  private ValidIdentity mockConstraintAnnotation(IdCodeValidationRuleSet... validationRuleSets) {
    var mock = mock(ValidIdentity.class);
    lenient().doReturn(Constants.ERROR_ID_CODE_INVALID).when(mock).message();
    lenient().doReturn(validationRuleSets).when(mock).idCodeValidationRuleSets();
    return mock;
  }
}
