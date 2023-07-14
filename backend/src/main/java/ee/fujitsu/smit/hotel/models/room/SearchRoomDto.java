package ee.fujitsu.smit.hotel.models.room;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
