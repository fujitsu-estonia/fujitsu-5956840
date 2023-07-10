package ee.fujitsu.smit.hotel.tools.validation;

import ee.fujitsu.smit.hotel.models.DateRange;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

class DateRangeValidatorTest extends ConstraintValidatorTest {

  @Test
  void test_whenStartDateAfterEndDate_isNotValid() {
    var validator = new DateRangeValidator();
    validator.initialize(mockConstraintAnnotation(0));

    var dateRange = new DateRange(LocalDate.of(2023, 4, 14), LocalDate.of(2023, 4, 12));

    var isValid = validator.isValid(dateRange, validationContext);

    assertFalse(isValid);

    assertNotNull(messageTemplateHolder.get());
    assertTrue(messageTemplateHolder.get().contains(Constants.ERROR_DATE_RANGE_INVALID));
    assertTrue(messageTemplateHolder.get().contains(Constants.ERROR_DATE_RANGE_ENDING_DATE_BEFORE));
  }

  @Test
  void test_whenDurationBetweenStartDateAndEndDateIsLessThanMinDaysValue_isNotValid() {
    var validator = new DateRangeValidator();
    validator.initialize(mockConstraintAnnotation(3));

    var dateRange = new DateRange(LocalDate.of(2023, 4, 12), LocalDate.of(2023, 4, 14));

    var isValid = validator.isValid(dateRange, validationContext);

    assertFalse(isValid);

    assertNotNull(messageTemplateHolder.get());
    assertTrue(messageTemplateHolder.get().contains(Constants.ERROR_DATE_RANGE_INVALID));
    assertTrue(messageTemplateHolder.get().contains(Constants.ERROR_DATE_RANGE_TOO_SHORT));
  }

  @Test
  void test_isValid() {
    var validator = new DateRangeValidator();
    validator.initialize(mockConstraintAnnotation(2));

    var dateRange = new DateRange(LocalDate.of(2023, 4, 12), LocalDate.of(2023, 5, 12));

    var isValid = validator.isValid(dateRange, validationContext);

    assertTrue(isValid);
    assertNull(messageTemplateHolder.get());

    dateRange = new DateRange(LocalDate.of(2023, 4, 12), LocalDate.of(2023, 4, 14));

    isValid = validator.isValid(dateRange, validationContext);

    assertTrue(isValid);
    assertNull(messageTemplateHolder.get());
  }

  private ValidDateRange mockConstraintAnnotation(long minDuration) {
    var mock = mock(ValidDateRange.class);
    lenient().doReturn(Constants.ERROR_DATE_RANGE_INVALID).when(mock).message();
    lenient().doReturn(minDuration).when(mock).minDays();
    return mock;
  }
}
