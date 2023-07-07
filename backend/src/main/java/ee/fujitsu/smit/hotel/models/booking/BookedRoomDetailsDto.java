package ee.fujitsu.smit.hotel.models.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedRoomDetailsDto {

  @Schema(type = "string", description = "Booked Room Type title")
  private String title;

  @Schema(type = "string", description = "Booked Room Type description")
  private String description;

  @Schema(type = "string", description = "Beds count")
  private Integer bedsCount;

  @Schema(type = "string", description = "Assigned room number")
  private String roomNumber;

  @Schema(type = "string", format = "url", description = "Room preview picture (link)")
  private String previewPictureUrl;

}
