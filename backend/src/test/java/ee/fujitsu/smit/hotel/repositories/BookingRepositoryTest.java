package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ee.fujitsu.smit.hotel.repositories.Conditions.id;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class BookingRepositoryTest {

  private Long roomTypeId;

  private Booking booking1;
  private Booking booking2;
  private Booking booking3;
  private Booking booking4;
  private Booking booking5;

  @Autowired private RoomRepository roomRepository;
  @Autowired private RoomTypeRepository roomTypeRepository;
  @Autowired private BookingRepository bookingRepository;

  @BeforeAll
  void prepareData() {
    var roomType =
        roomTypeRepository.save(
            RoomType.builder().title("test").bedsCount(2).pricePerNight(1.0).build());
    roomTypeId = roomType.getId();

    var room1 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("1").build());
    var room2 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("2").build());
    var room3 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("3").build());
    var room4 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("4").build());
    var room5 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("5").build());

    booking1 =
        bookingRepository.saveAndFlush(
            booking(roomType, room1)
                .status(BookingStatus.ACCEPTED)
                .startDate(LocalDateTime.of(2023, 4, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 4, 19, 10, 0))
                .build());
    booking2 =
        bookingRepository.saveAndFlush(
            booking(roomType, room2)
                .status(BookingStatus.STARTED)
                .startDate(LocalDateTime.of(2023, 4, 18, 12, 0))
                .endDate(LocalDateTime.of(2023, 5, 1, 10, 0))
                .build());
    booking3 =
        bookingRepository.saveAndFlush(
            booking(roomType, room3)
                .status(BookingStatus.FINISHED)
                .startDate(LocalDateTime.of(2023, 3, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 3, 19, 10, 0))
                .build());
    booking4 =
        bookingRepository.saveAndFlush(
            booking(roomType, room4)
                .status(BookingStatus.CANCELLED_BY_USER)
                .startDate(LocalDateTime.of(2023, 4, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 4, 19, 10, 0))
                .build());
    booking5 =
        bookingRepository.saveAndFlush(
            booking(roomType, room5)
                .status(BookingStatus.CANCELLED_BY_ADMIN)
                .startDate(LocalDateTime.of(2023, 3, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 4, 17, 10, 0))
                .build());
  }

  @Test
  void findByStatusAndTimeBounds_withStatusesParam() {
    var search = SearchBookingsDto.builder().bookingStatus(BookingStatus.ACCEPTED).build();

    var found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).hasSize(1).first().has(id(booking1.getId()));

    search =
        SearchBookingsDto.builder()
            .bookingStatus(BookingStatus.STARTED)
            .bookingStatus(BookingStatus.CANCELLED_BY_USER)
            .build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found)
        .hasSize(2)
        .map(Booking::getId)
        .containsOnly(booking2.getId(), booking4.getId());
  }

  @Test
  void findByStatusAndTimeBounds_withFromDateParam() {
    var search = SearchBookingsDto.builder().fromDate(LocalDate.of(2023, 1, 1)).build();

    var found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).hasSize(5);

    search = SearchBookingsDto.builder().fromDate(LocalDate.of(2024, 1, 1)).build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).isEmpty();

    search = SearchBookingsDto.builder().fromDate(LocalDate.of(2023, 4, 16)).build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found)
        .hasSize(3)
        .map(Booking::getStartDate)
        .allMatch(
            startDate ->
                startDate.getMonthValue() > 4
                    || startDate.getMonthValue() == 4 && startDate.getDayOfMonth() >= 16);
  }

  @Test
  void findByStatusAndTimeBounds_withToDateParam() {
    var search = SearchBookingsDto.builder().toDate(LocalDate.of(2023, 1, 1)).build();

    var found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).isEmpty();

    search = SearchBookingsDto.builder().toDate(LocalDate.of(2024, 1, 1)).build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).hasSize(5);

    search = SearchBookingsDto.builder().toDate(LocalDate.of(2023, 4, 19)).build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found)
        .hasSize(2)
        .map(Booking::getEndDate)
        .allMatch(
            endDate ->
                endDate.getMonthValue() < 4
                    || endDate.getMonthValue() == 4 && endDate.getDayOfMonth() < 19);
  }

  @Test
  void findByStatusAndTimeBounds_withTimeBounds() {
    var builder =
        SearchBookingsDto.builder()
            .fromDate(LocalDate.of(2023, 4, 16))
            .toDate(LocalDate.of(2023, 4, 19));

    var search = builder.build();

    var found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).isEmpty();

    search = builder.toDate(LocalDate.of(2023, 4, 20)).build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found)
        .hasSize(2)
        .map(Booking::getId)
        .containsOnly(booking1.getId(), booking4.getId());
  }

  @Test
  void findByStatusAndTimeBounds_allParams() {
    var builder =
        SearchBookingsDto.builder()
            .fromDate(LocalDate.of(2023, 3, 16))
            .toDate(LocalDate.of(2023, 4, 18))
            .bookingStatus(BookingStatus.ACCEPTED)
            .bookingStatus(BookingStatus.STARTED)
            .bookingStatus(BookingStatus.FINISHED);

    var search = builder.build();

    var found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).hasSize(1).first().has(id(booking3.getId()));

    search =
        builder
            .clearBookingStatuses()
            .bookingStatus(BookingStatus.CANCELLED_BY_USER)
            .bookingStatus(BookingStatus.CANCELLED_BY_ADMIN)
            .build();

    found = bookingRepository.findByStatusAndTimeBounds(search);

    assertThat(found).hasSize(1).first().has(id(booking5.getId()));
  }

  @Test
  void countAvailableRoomsOfTypeForPeriod_finishedAndCancelledAreCountedAsAvailable() {
    var count =
        bookingRepository.countAvailableRoomsOfTypeForPeriod(
            roomTypeId,
            LocalDate.of(2022, 1, 1).atStartOfDay(),
            LocalDate.of(2024, 1, 1).atStartOfDay());

    assertEquals(3, count);
  }

  @Test
  void countAvailableRoomsOfTypeForPeriod_timeBoundsTest() {
    var count =
        bookingRepository.countAvailableRoomsOfTypeForPeriod(
            roomTypeId, LocalDateTime.of(2023, 4, 15, 12, 0), LocalDateTime.of(2023, 4, 18, 10, 0));

    assertEquals(4, count); // booking1 is overlapping (thus -1 room), booking2 isn't
  }

  @Test
  void countAvailableRoomsOfTypeForPeriod_testRoomTypeFiltering() {
    var count =
        bookingRepository.countAvailableRoomsOfTypeForPeriod(
            roomTypeId + 1,
            LocalDate.of(2022, 1, 1).atStartOfDay(),
            LocalDate.of(2024, 1, 1).atStartOfDay());

    assertEquals(0, count); // no rooms for type
  }

  private Booking.BookingBuilder booking(RoomType roomType, Room room) {
    return Booking.builder()
        .roomType(roomType)
        .assignedRoom(room)
        .firstName("firstName")
        .lastName("lastName")
        .email("firstName.lastName@test");
  }
}
