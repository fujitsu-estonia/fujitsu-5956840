package ee.fujitsu.smit.hotel.models.room;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchRoomDto {

  @Schema(type = "integer", description = "Beds count", example = "2")
  private Integer beds;

  @Schema(
      type = "string",
      format = "date",
      description = "Booking start date",
      example = "2023-07-05")
  private LocalDate startDate;

  @Schema(
      type = "string",
      format = "date",
      description = "Booking end date",
      example = "2023-07-07")
  private LocalDate endDate;
}
