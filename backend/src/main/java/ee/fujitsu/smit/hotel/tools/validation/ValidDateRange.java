package ee.fujitsu.smit.hotel.tools.validation;

import ee.fujitsu.smit.hotel.tools.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {

  String message() default Constants.ERROR_DATE_RANGE_ENDING_DATE_BEFORE;

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  long minDays() default 1;
  
}
