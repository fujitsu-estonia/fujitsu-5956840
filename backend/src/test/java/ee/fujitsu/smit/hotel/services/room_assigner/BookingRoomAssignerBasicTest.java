package ee.fujitsu.smit.hotel.services.room_assigner;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import ee.fujitsu.smit.hotel.services.impl.BookingRoomAssigner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingRoomAssignerBasicTest {

  @Mock private RoomRepository roomRepository;

  private BookingRoomAssigner roomAssigner;

  @BeforeEach
  void setUp() {
    roomAssigner = new BookingRoomAssigner(roomRepository);
  }

  @Test
  void test_assignAvailableRoom_whenRoomAlreadyAssigned_thenItIsNotChanged() {
    var booking = booking(new RoomType(), Room.builder().id(1L).build()).build();

    roomAssigner.assignAvailableRoom(booking);

    assertEquals(1L, booking.getAssignedRoom().getId());
  }

  @Test
  void test_assignAvailableRoom_whenRoomTypeNotDefined_throwsIllegalStateEx() {
    var booking = booking(null, null).build();

    assertThrows(IllegalStateException.class, () -> roomAssigner.assignAvailableRoom(booking));
  }

  @Test
  void test_assignAvailableRoom_firstAvailableRoomGetsAssigned() {
    var roomType = RoomType.builder().id(1L).build();
    var from = LocalDateTime.now();
    var to = from.plusDays(3);
    var booking = booking(roomType, null).startDate(from).endDate(to).build();

    var results =
        List.of(
            Room.builder().id(1L).roomNumber("1").build(),
            Room.builder().id(2L).roomNumber("2").build());

    when(roomRepository.findAvailableRoomsOfTypeByPeriod(roomType, from, to)).thenReturn(results);

    roomAssigner.assignAvailableRoom(booking);

    assertEquals(1L, booking.getAssignedRoom().getId());
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
