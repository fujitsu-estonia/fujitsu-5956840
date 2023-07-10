package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDetailsDto {

  @Schema(type = "integer", format = "int64", description = "Room type id", example = "1")
  private Long roomTypeId;

  @Schema(type = "string", description = "Room title", example = "Nice big room")
  private String title;

  @Schema(type = "string", description = "Room description", example = "Nice big room with 2 small beds")
  private String description;

  @Schema(type = "integer", description = "Beds count", example = "2")
  private Integer bedsCount;

  @Schema(type = "number", format = "double", description = "Room price per night", example = "79.00")
  private Double pricePerNight;

  @Schema(type = "string", description = "Room preview picture url")
  private String previewPictureUrl;

  @Schema(type = "integer", description = "Count of such a type of free rooms", example = "3")
  private Integer freeRooms;
}
