package ee.fujitsu.smit.hotel.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import ee.fujitsu.smit.hotel.repositories.models.RoomTypeExtended;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RoomTypeRepositoryTest extends DataManipulationTestBase {
  @Test
  void getAvailableRoomTypesByBedsCountForPeriod_withBedsParam() {
    var builder = SearchRoomDto.builder().beds(2);
    var searchParams = builder.build();
    var found =
        roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(
            searchParams.getBeds(), searchParams.getStartDate(), searchParams.getEndDate());

    assertThat(found).hasSize(1);
    checkRoomType(found, data.roomTypes().get(0), 5, 0);
  }

  @Test
  void getAvailableRoomTypesByBedsCountForPeriod_withTimeBounds() {
    var builder =
        SearchRoomDto.builder()
            .startDate(LocalDate.of(2023, 3, 16))
            .endDate(LocalDate.of(2023, 4, 18));
    var searchParams = builder.build();
    var found =
        roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(
            searchParams.getBeds(), searchParams.getStartDate(), searchParams.getEndDate());

    assertThat(found).hasSize(2);
    checkRoomType(found, data.roomTypes().get(0), 5, 1);
  }

  @Test
  void getAvailableRoomTypesByBedsCountForPeriod_allParams() {
    var builder =
        SearchRoomDto.builder()
            .beds(2)
            .startDate(LocalDate.of(2023, 3, 16))
            .endDate(LocalDate.of(2023, 4, 18));
    var searchParams = builder.build();
    var found =
        roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(
            searchParams.getBeds(), searchParams.getStartDate(), searchParams.getEndDate());

    assertThat(found).hasSize(1);
    checkRoomType(found, data.roomTypes().get(0), 5, 1);
  }

  private void checkRoomType(List<RoomTypeExtended> found, RoomType roomType, int allRooms, int bookedRooms) {
    assertThat(found)
        .first()
        .matches(roomTypeExtended -> roomTypeExtended.getRoomTypeId().equals(roomType.getId()))
        .matches(roomTypeExtended -> roomTypeExtended.getTitle().equals(roomType.getTitle()))
        .matches(roomTypeExtended -> roomTypeExtended.getDescriptionStr().equals(roomType.getDescription()))
        .matches(roomTypeExtended -> roomTypeExtended.getBedsCount().equals(roomType.getBedsCount()))
        .matches(roomTypeExtended -> roomTypeExtended.getPricePerNight().equals(roomType.getPricePerNight()))
        .matches(roomTypeExtended -> roomTypeExtended.getPreviewPictureUrl().equals(roomType.getPreviewPictureUrl()))
        .matches(roomTypeExtended -> roomTypeExtended.getAllRooms().equals(allRooms))
        .matches(roomTypeExtended -> roomTypeExtended.getBookedRooms() == null || roomTypeExtended.getBookedRooms() == bookedRooms)
        .matches(roomTypeExtended -> roomTypeExtended.getFreeRooms().equals(allRooms - bookedRooms));
  }
}
