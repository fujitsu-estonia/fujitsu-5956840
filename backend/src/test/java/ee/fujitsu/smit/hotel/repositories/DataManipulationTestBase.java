package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DataManipulationTestBase {

  public record Data(List<RoomType> roomTypes, List<Room> rooms, List<Booking> bookings) {}

  @Autowired protected RoomRepository roomRepository;
  @Autowired protected RoomTypeRepository roomTypeRepository;
  @Autowired protected BookingRepository bookingRepository;

  protected final Data data = new Data(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

  @BeforeAll
  protected void prepareData() {
    var roomType =
        roomTypeRepository.save(
            RoomType.builder().title("test").bedsCount(2).pricePerNight(1.0).build());
    data.roomTypes().add(roomType);

    var room1 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("1").build());
    var room2 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("2").build());
    var room3 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("3").build());
    var room4 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("4").build());
    var room5 = roomRepository.save(Room.builder().roomType(roomType).roomNumber("5").build());

    data.rooms().addAll(List.of(room1, room2, room3, room4, room5));

    var booking1 =
        bookingRepository.saveAndFlush(
            booking(roomType, room1)
                .status(BookingStatus.ACCEPTED)
                .startDate(LocalDateTime.of(2023, 4, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 4, 19, 10, 0))
                .build());
    var booking2 =
        bookingRepository.saveAndFlush(
            booking(roomType, room2)
                .status(BookingStatus.STARTED)
                .startDate(LocalDateTime.of(2023, 4, 18, 12, 0))
                .endDate(LocalDateTime.of(2023, 5, 1, 10, 0))
                .build());
    var booking3 =
        bookingRepository.saveAndFlush(
            booking(roomType, room3)
                .status(BookingStatus.FINISHED)
                .startDate(LocalDateTime.of(2023, 3, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 3, 19, 10, 0))
                .build());
    var booking4 =
        bookingRepository.saveAndFlush(
            booking(roomType, room4)
                .status(BookingStatus.CANCELLED_BY_USER)
                .startDate(LocalDateTime.of(2023, 4, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 4, 19, 10, 0))
                .build());
    var booking5 =
        bookingRepository.saveAndFlush(
            booking(roomType, room5)
                .status(BookingStatus.CANCELLED_BY_ADMIN)
                .startDate(LocalDateTime.of(2023, 3, 16, 12, 0))
                .endDate(LocalDateTime.of(2023, 4, 17, 10, 0))
                .build());

    data.bookings().addAll(List.of(booking1, booking2, booking3, booking4, booking5));
  }

  @AfterAll
  void tearDown() {
    bookingRepository.deleteAll();
    roomRepository.deleteAll();
    roomTypeRepository.deleteAll();
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
