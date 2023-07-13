package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RoomRepositoryTest extends DataManipulationTestBase {

  private RoomType roomType;

  @BeforeAll
  void setUp() {
    roomType = data.roomTypes().get(0);
  }

  @Test
  void countAvailableRoomsOfTypeForPeriod_finishedAndCancelledAreCountedAsAvailable() {
    var foundRooms =
        roomRepository
            .findAvailableRoomsOfTypeByPeriod(
                roomType,
                LocalDate.of(2022, 1, 1).atStartOfDay(),
                LocalDate.of(2024, 1, 1).atStartOfDay());

    assertThat(foundRooms)
        .hasSize(3)
        .noneMatch(room -> room.getRoomNumber().equals("1"))
        .noneMatch(room -> room.getRoomNumber().equals("2"));
  }

  @Test
  void countAvailableRoomsOfTypeForPeriod_timeBoundsTest() {
    var foundRooms =
        roomRepository
            .findAvailableRoomsOfTypeByPeriod(
                roomType,
                LocalDateTime.of(2023, 4, 15, 12, 0),
                LocalDateTime.of(2023, 4, 18, 10, 0));

    assertThat(foundRooms)
        .hasSize(4) // booking1 is overlapping (thus -1 room), booking2 isn't
        .noneMatch(room -> room.getRoomNumber().equals("1"));
  }

  @Test
  void countAvailableRoomsOfTypeForPeriod_testRoomTypeFiltering() {
    var roomType =
        roomTypeRepository.save(
            RoomType.builder().title("withoutRooms").bedsCount(3).pricePerNight(1D).build());

    var count =
        roomRepository
            .findAvailableRoomsOfTypeByPeriod(
                roomType,
                LocalDate.of(2022, 1, 1).atStartOfDay(),
                LocalDate.of(2024, 1, 1).atStartOfDay())
            .size();

    assertEquals(0L, count); // no rooms for type
  }

}
