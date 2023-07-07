package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record DateRange(
    @Schema(type = "string", format = "date", description = "Starting date", example = "2023-07-07")
        LocalDate startDate,
    @Schema(type = "string", format = "date", description = "End date", example = "2023-07-07")
        LocalDate endDate) {

        public long durationInDays() {
                return ChronoUnit.DAYS.between(startDate, endDate);
        }

}
