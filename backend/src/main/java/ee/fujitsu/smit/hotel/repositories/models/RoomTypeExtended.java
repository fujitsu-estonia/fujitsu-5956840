package ee.fujitsu.smit.hotel.repositories.models;

import java.sql.NClob;

public interface RoomTypeExtended {
  Long getRoomTypeId();

  String getTitle();

  NClob getDescription();

  Integer getBedsCount();

  Double getPricePerNight();

  String getPreviewPictureUrl();

  Integer getAllRooms();

  Integer getBookedRooms();

  default Integer getFreeRooms() {
    if (getAllRooms() == null) {
      return 0;
    }
    return getAllRooms() - (getBookedRooms() == null ? 0 : getBookedRooms());
  }

  default String getDescriptionStr() {
    return getDescription().toString();
  }
}
