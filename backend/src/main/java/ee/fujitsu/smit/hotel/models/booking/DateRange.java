package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.tools.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record DateRange(
    @Schema(
            type = "string",
            format = "date",
            description = "Starting date (future or present)",
            example = "2023-07-07")
        @FutureOrPresent(message = Constants.ERROR_DATE_NOT_IN_FUTURE_NOR_PRESENT)
        LocalDate startDate,
    @Schema(
            type = "string",
            format = "date",
            description = "End date (future)",
            example = "2023-07-07")
        @FutureOrPresent(message = Constants.ERROR_DATE_NOT_IN_FUTURE_NOR_PRESENT)
        LocalDate endDate) {

  public long durationInDays() {
    return ChronoUnit.DAYS.between(startDate, endDate);
  }
}
