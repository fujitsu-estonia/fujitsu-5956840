package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.models.room.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import java.util.List;
import lombok.NonNull;

public interface RoomService {

  /**
   * Find room types that match given {@link SearchRoomDto search parameters}
   *
   * @param searchRoom room types search parameters
   * @return found room types list
   */
  @NonNull
  List<RoomDetailsDto> findRoomByParameters(@NonNull SearchRoomDto searchRoom);

  /**
   * Get room type details by id.
   *
   * @param roomTypeId room type id
   * @return founds room type or {@code null} if nothing was found
   */
  RoomDetailsDto getRoomDetails(long roomTypeId);
}
