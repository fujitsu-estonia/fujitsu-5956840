package ee.fujitsu.smit.hotel.models.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record DateRange(
    @Schema(type = "string", format = "date", description = "Starting date (future or present)", example = "2023-07-07")
        @FutureOrPresent
        LocalDate startDate,
    @Schema(type = "string", format = "date", description = "End date (future)", example = "2023-07-07")
        @FutureOrPresent
        LocalDate endDate) {

  public long durationInDays() {
    return ChronoUnit.DAYS.between(startDate, endDate);
  }
}
