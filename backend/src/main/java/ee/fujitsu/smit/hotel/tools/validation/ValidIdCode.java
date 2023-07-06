package ee.fujitsu.smit.hotel.tools.validation;

import ee.fujitsu.smit.hotel.tools.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Validates ID codes based on selected {@link ValidationRuleSet rule sets} */
@Documented
@Constraint(validatedBy = IdCodeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdCode {

  String message() default Constants.ERROR_ID_CODE_INVALID;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * What rule sets a passed value can follow, a.k.a. allowed validation strategies for passed value
   */
  ValidationRuleSet[] allowedRuleSets() default {ValidationRuleSet.ESTONIAN_NATIONAL_ID};

  enum ValidationRuleSet {
    ESTONIAN_NATIONAL_ID,
    OTHER
  }
}
