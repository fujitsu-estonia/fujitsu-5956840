package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class RoomDetailsDto {

  @Schema(type = "integer", format = "int64", description = "Room type id", example = "1")
  private Long roomTypeId;

  @Schema(type = "string", description = "Room description", example = "Nice big room")
  private String description;

  @Schema(type = "integer", description = "Beds count", example = "2")
  private Integer beds;

  @Schema(type = "number", format = "double", description = "Room price", example = "79.00")
  private Double price;

  @Schema(type = "integer", description = "Count of such a type of free rooms", example = "3")
  private Integer freeRooms;
}
