package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRange {

  @Schema(
      type = "string",
      format = "date-time",
      description = "Starting date&time",
      example = "2023-07-05T12:00:00Z")
  private LocalDateTime startDate;

  @Schema(
      type = "string",
      format = "date-time",
      description = "End date&time",
      example = "2023-07-07T12:00:00Z")
  private LocalDateTime endDate;

}
