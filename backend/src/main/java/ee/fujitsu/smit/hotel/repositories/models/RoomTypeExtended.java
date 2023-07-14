package ee.fujitsu.smit.hotel.repositories.models;

import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.time.LocalDate;

/**
 * Interface describes return columns of {@link
 * RoomTypeRepository#getAvailableRoomTypesByBedsCountForPeriod(Integer, LocalDate, LocalDate)}
 * query.
 */
public interface RoomTypeExtended {
  Long getRoomTypeId();

  String getTitle();

  Clob getDescription();

  Integer getBedsCount();

  Double getPricePerNight();

  String getPreviewPictureUrl();

  Integer getAllRooms();

  Integer getBookedRooms();

  default int getFreeRooms() {
    if (getAllRooms() == null) {
      return 0;
    }
    if (getBookedRooms() == null) {
      return getAllRooms();
    }
    return getAllRooms() - getBookedRooms();
  }

  @SneakyThrows
  default String getDescriptionStr() {
    var clob = getDescription();
    if (clob == null) {
      return "";
    }

    Reader reader = clob.getCharacterStream();
    StringWriter writer = new StringWriter();
    IOUtils.copy(reader, writer);
    return writer.toString();
  }
}
