package ee.fujitsu.smit.hotel.tools.validation;

import ee.fujitsu.smit.hotel.models.booking.DateRange;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {

  private long minDuration;

  @Override
  public void initialize(ValidDateRange anno) {
    ConstraintValidator.super.initialize(anno);
    minDuration = anno.minDays();
  }

  @Override
  public boolean isValid(DateRange dateRange, ConstraintValidatorContext context) {
    if (dateRange == null) {
      return true;
    }
    if (dateRange.startDate() == null || dateRange.endDate() == null) {
      return false;
    }
    return isValidInternal(dateRange, context);
  }

  private boolean isValidInternal(DateRange dateRange, ConstraintValidatorContext context) {
    if (dateRange.startDate().isAfter(dateRange.endDate())) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(Constants.ERROR_DATE_RANGE_ENDING_DATE_BEFORE)
          .addConstraintViolation();
      return false;
    }
    if (dateRange.durationInDays() < minDuration) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(Constants.ERROR_DATE_RANGE_TOO_SHORT)
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
