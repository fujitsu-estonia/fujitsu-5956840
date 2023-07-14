package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.repositories.models.RoomTypeExtended;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class RoomTypeRepositoryTest extends DataManipulationTestBase {
  @Test
  void getAvailableRoomTypesByBedsCountForPeriod_withBedsParam() {
    var bedsCount = 2;
    var found = roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(bedsCount, null, null);

    var expectedRoomType = data.roomTypes().get(0);

    assertThat(found)
        .hasSize(1)
        .first()
        .satisfies(roomTypeExtended -> assertRoomType(expectedRoomType, roomTypeExtended))
        .satisfies(roomTypeExtended -> assertEquals(5, roomTypeExtended.getAllRooms()))
        .satisfies(roomTypeExtended -> assertNull(roomTypeExtended.getBookedRooms()))
        .satisfies(roomTypeExtended -> assertEquals(5, roomTypeExtended.getFreeRooms()));
  }

  @Test
  void getAvailableRoomTypesByBedsCountForPeriod_withTimeBounds() {
    var startDate = LocalDate.of(2023, 3, 16);
    var endDate = LocalDate.of(2023, 4, 18);

    var found =
        roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(null, startDate, endDate);

    assertThat(found)
        .hasSize(2)
        .anySatisfy(
            roomTypeExtended -> {
              assertRoomType(data.roomTypes().get(0), roomTypeExtended);
              assertEquals(5, roomTypeExtended.getAllRooms());
              assertEquals(1, roomTypeExtended.getBookedRooms());
              assertEquals(4, roomTypeExtended.getFreeRooms());
            })
        .anySatisfy(
            roomTypeExtended -> {
              assertRoomType(data.roomTypes().get(1), roomTypeExtended);
              assertEquals(2, roomTypeExtended.getAllRooms());
              assertNull(roomTypeExtended.getBookedRooms());
              assertEquals(2, roomTypeExtended.getFreeRooms());
            });
  }

  @Test
  void getAvailableRoomTypesByBedsCountForPeriod_allParams() {
    var bedsCount = 2;
    var startDate = LocalDate.of(2023, 3, 16);
    var endDate = LocalDate.of(2023, 4, 18);

    var found =
        roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(bedsCount, startDate, endDate);

    var expectedRoomType = data.roomTypes().get(0);

    assertThat(found)
        .hasSize(1)
        .first()
        .satisfies(rt -> assertRoomType(expectedRoomType, rt))
        .satisfies(rt -> assertEquals(5, rt.getAllRooms()))
        .satisfies(rt -> assertEquals(1, rt.getBookedRooms()))
        .satisfies(rt -> assertEquals(4, rt.getFreeRooms()));
  }

  private static void assertRoomType(RoomType expected, RoomTypeExtended actual) {
    assertThat(actual)
        .matches(roomTypeExtended -> roomTypeExtended.getRoomTypeId().equals(expected.getId()))
        .matches(roomTypeExtended -> roomTypeExtended.getTitle().equals(expected.getTitle()))
        .matches(
            roomTypeExtended ->
                roomTypeExtended.getDescriptionStr().equals(expected.getDescription()))
        .matches(
            roomTypeExtended -> roomTypeExtended.getBedsCount().equals(expected.getBedsCount()))
        .matches(
            roomTypeExtended ->
                roomTypeExtended.getPricePerNight().equals(expected.getPricePerNight()))
        .matches(
            roomTypeExtended ->
                roomTypeExtended.getPreviewPictureUrl().equals(expected.getPreviewPictureUrl()));
  }
}
