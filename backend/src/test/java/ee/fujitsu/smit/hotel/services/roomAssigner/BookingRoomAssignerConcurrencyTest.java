package ee.fujitsu.smit.hotel.services.roomAssigner;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.room.NoAvailableRoomsException;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.repositories.DataManipulationTestBase;
import ee.fujitsu.smit.hotel.services.impl.BookingRoomAssigner;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookingRoomAssignerConcurrencyTest extends DataManipulationTestBase {
  private static final int ROOMS_COUNT = 40;

  private BookingRoomAssigner roomAssigner;

  private RoomType roomType;

  @BeforeEach
  @Override
  protected void prepareData() {
    roomAssigner = new BookingRoomAssigner(roomRepository);

    roomType =
        roomTypeRepository.save(
            RoomType.builder().title("test").bedsCount(2).pricePerNight(12.3).build());

    for (int i = 1; i <= ROOMS_COUNT; i++) {
      roomRepository.save(
          Room.builder().roomType(roomType).roomNumber(String.format("%02d", i)).build());
    }
  }

  @Test
  @DisplayName("Ensure that rooms are not double-booked in case of simultaneous requests")
  @SneakyThrows
  void test_whenAssigningRoomsSimultaneously_thenRoomsAreNotRepeated() {
    int overLimit = 10; // amount of bookings that should exceed available rooms count
    int differentPeriodBookings = 20;
    int bookersCount = ROOMS_COUNT + overLimit + differentPeriodBookings;

    // in order release threads execution at approximately same time
    var gate = new CyclicBarrier(bookersCount + 1);
    var latch = new CountDownLatch(bookersCount);

    var start = LocalDate.now().atTime(12, 0);
    var end = start.plusDays(2);
    var bookersForFirstPeriod =
        createBookersForPeriod(ROOMS_COUNT + overLimit, start, end, gate, latch);

    start = LocalDate.now().plusDays(2).atTime(13, 0);
    end = start.plusDays(2);
    var bookersForOtherPeriod =
        createBookersForPeriod(differentPeriodBookings, start, end, gate, latch);

    gate.await(); // release all booker threads
    latch.await(); // await for the results

    // all rooms should be booked for the first booking period
    assertEquals(
        ROOMS_COUNT,
        bookersForFirstPeriod.stream().filter(DummyBooker::isSuccessfullyBooked).count());

    var assignedRooms =
        bookersForFirstPeriod.stream()
            .map(DummyBooker::getSavedBooking)
            .map(AtomicReference::get)
            .filter(Objects::nonNull)
            .map(Booking::getAssignedRoom)
            .map(Room::getId)
            .toList();
    // all booked rooms must be unique
    assertEquals(new HashSet<>(assignedRooms).size(), assignedRooms.size());

    var noRoomAvailable =
        bookersForFirstPeriod.stream().filter(not(DummyBooker::isSuccessfullyBooked)).toList();
    // After rooms limit for period is exceeded, remaining bookers should get
    // NoAvailableRoomsException
    assertThat(noRoomAvailable)
        .hasSize(overLimit)
        .map(DummyBooker::getCaughtException)
        .map(AtomicReference::get)
        .allMatch(ex -> ex instanceof NoAvailableRoomsException)
        .allMatch(ex -> ex.getMessage().contains(Constants.ERROR_NO_ROOMS_AVAILABLE_FOR_BOOKING));

    // first period rooms reservations should not affect other period bookings (rooms should be
    // available for booking for other period)
    assertThat(bookersForOtherPeriod)
        .map(DummyBooker::isSuccessfullyBooked)
        .allMatch(result -> result);
  }

  public List<DummyBooker> createBookersForPeriod(
      int bookersCount,
      LocalDateTime start,
      LocalDateTime end,
      CyclicBarrier gate,
      CountDownLatch latch) {
    var bookers = new ArrayList<DummyBooker>();
    for (int i = 0; i < bookersCount; i++) {
      var booker =
          new DummyBooker(gate, latch, bookingRepository, roomAssigner, roomType, start, end);
      bookers.add(booker);
      booker.start();
    }
    return bookers;
  }

  @RequiredArgsConstructor
  public static class DummyBooker extends Thread {

    private final CyclicBarrier gate;
    private final CountDownLatch latch;

    private final BookingRepository bookingRepository;
    private final BookingRoomAssigner roomAssigner;

    private final RoomType roomType;
    private final LocalDateTime start;
    private final LocalDateTime end;

    @Getter private final AtomicReference<Booking> savedBooking = new AtomicReference<>();
    @Getter private final AtomicReference<Throwable> caughtException = new AtomicReference<>();

    public boolean isSuccessfullyBooked() {
      return savedBooking.get() != null && caughtException.get() == null;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void run() {
      gate.await();
      try {
        savedBooking.set(createBooking());
      } catch (NoAvailableRoomsException ex) {
        caughtException.set(ex);
      } finally {
        latch.countDown();
      }
    }

    private Booking createBooking() {
      var booking =
          Booking.builder()
              .status(BookingStatus.ACCEPTED)
              .roomType(roomType)
              .startDate(start)
              .endDate(end)
              .firstName("firstName")
              .lastName("lastName")
              .email("firstname.lastName@email")
              .build();

      roomAssigner.assignAvailableRoom(booking);

      return bookingRepository.saveAndFlush(booking);
    }
  }
}
