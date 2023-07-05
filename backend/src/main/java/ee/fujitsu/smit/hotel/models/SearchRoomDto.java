package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchRoomDto {

  @Schema(type = "integer", description = "Beds count", example = "2")
  private Integer beds;

  @Schema(type = "string", format = "date-time", description = "Booking start date", example = "2023-07-05T12:00:00Z")
  private LocalDateTime startDate;

  @Schema(type = "string", format = "date-time", description = "Booking end date", example = "2023-07-07T12:00:00Z")
  private LocalDateTime endDate;
}
