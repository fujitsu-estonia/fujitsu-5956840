package ee.fujitsu.smit.hotel.services.room_assigner;

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
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookingRoomAssignerConcurrencyTest extends DataManipulationTestBase {
  private static final int ROOMS_COUNT_1 = 30;
  private static final int ROOMS_COUNT_2 = 10;

  private BookingRoomAssigner roomAssigner;

  private RoomType roomType1;
  private RoomType roomType2;

  @BeforeEach
  @Override
  protected void prepareData() {
    roomAssigner = new BookingRoomAssigner(roomRepository);

    roomType1 =
        roomTypeRepository.save(
            RoomType.builder().title("test1").bedsCount(2).pricePerNight(12.3).build());

    roomType2 =
        roomTypeRepository.save(
            RoomType.builder().title("test2").bedsCount(3).pricePerNight(45.6).build());

    for (int i = 1; i <= ROOMS_COUNT_1; i++) {
      roomRepository.save(
          Room.builder().roomType(roomType1).roomNumber(String.format("%02d", i)).build());
    }

    for (int i = ROOMS_COUNT_1 + 1; i <= ROOMS_COUNT_1 + ROOMS_COUNT_2; i++) {
      roomRepository.save(
          Room.builder().roomType(roomType2).roomNumber(String.format("%02d", i)).build());
    }
  }

  /**
   * Purpose of this test is to ensure that roomAssigner will work correctly under many simultaneous
   * requests.
   *
   * <p>Inputs:
   *
   * <ul>
   *   <li>room type '1', with 30 rooms available
   *   <li>Room type '2', with 10 rooms available
   *   <li>40 'users' who want to book the room of room type '1' for the same or partially
   *       overlapping period (1-2 days difference, but some dates are always common)
   *   <li>10 'users' who want to book room of room type '2' for same/partially overlapping period
   *       as first 40 users
   *   <li>10 'users' who want to book the room of room type '1' for another period, which is not
   *       overlapping with the first completely
   * </ul>
   *
   * <p>In total 60 requests are emitted simultaneously with the help of {@link CyclicBarrier}
   *
   * <p>Expected results
   *
   * <ul>
   *   <li>All 30 rooms of room type '1' are booked, and each room is booked only once (for the
   *       first period)
   *   <li>10 'users', who attempted to book the room of type '1' for same/overlapping period, got
   *       {@link * NoAvailableRoomsException}
   *   <li>10 'users' who booked rooms of type '1' for another period, will all book the rooms
   *       successfully, * because the rooms are not busy for that period
   *   <li>All 10 rooms of room type '2' are booked, and each room is booked only once
   * </ul>
   */
  @Test
  @SneakyThrows
  void test_whenAssigningRoomsSimultaneously_thenRoomsAreNotRepeated() {
    int overLimit = 10; // amount of bookings that should exceed available rooms count
    int differentPeriodBookings = 10;
    int bookersCount = ROOMS_COUNT_1 + overLimit + differentPeriodBookings + ROOMS_COUNT_2;

    // in order release threads execution at approximately same time
    var gate = new CyclicBarrier(bookersCount + 1);
    var latch = new CountDownLatch(bookersCount);

    var start = LocalDate.now().atTime(12, 0);
    var end = start.plusDays(6);
    var bookersForRoomType1ForFirstPeriod =
        createBookersForPeriod(roomType1, ROOMS_COUNT_1 + overLimit, start, end, gate, latch);
    var bookersForRoomType2ForFirstPeriod =
        createBookersForPeriod(roomType2, ROOMS_COUNT_2, start, end, gate, latch);

    start = LocalDate.now().plusMonths(1).atTime(13, 0);
    end = start.plusDays(6);
    var bookersForRoomType1ForOtherPeriod =
        createBookersForPeriod(roomType1, differentPeriodBookings, start, end, gate, latch);

    gate.await(); // release all booker threads
    latch.await(); // await for the results

    // all rooms should be booked for the first booking period
    assertEquals(
        ROOMS_COUNT_1,
        bookersForRoomType1ForFirstPeriod.stream()
            .filter(DummyBooker::isSuccessfullyBooked)
            .count());

    assertDoesNotContainDoubleBookedRooms(bookersForRoomType1ForFirstPeriod);

    var noRoomAvailable =
        bookersForRoomType1ForFirstPeriod.stream()
            .filter(not(DummyBooker::isSuccessfullyBooked))
            .toList();
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
    assertThat(bookersForRoomType1ForOtherPeriod)
        .hasSize(differentPeriodBookings)
        .allMatch(DummyBooker::isSuccessfullyBooked);

    assertEquals(
        ROOMS_COUNT_2,
        bookersForRoomType2ForFirstPeriod.stream()
            .filter(DummyBooker::isSuccessfullyBooked)
            .count());

    assertDoesNotContainDoubleBookedRooms(bookersForRoomType2ForFirstPeriod);
  }

  private void assertDoesNotContainDoubleBookedRooms(List<DummyBooker> bookers) {
    var assignedRooms =
        bookers.stream()
            .map(DummyBooker::getSuccessfulBooking)
            .map(AtomicReference::get)
            .filter(Objects::nonNull)
            .map(Booking::getAssignedRoom)
            .map(Room::getId)
            .toList();
    // all booked rooms must be unique
    assertEquals(new HashSet<>(assignedRooms).size(), assignedRooms.size());
  }

  public List<DummyBooker> createBookersForPeriod(
      RoomType roomType,
      int bookersCount,
      LocalDateTime start,
      LocalDateTime end,
      CyclicBarrier gate,
      CountDownLatch latch) {
    var random = new Random();
    var bookers = new ArrayList<DummyBooker>();
    for (int i = 0; i < bookersCount; i++) {
      var startRnd = start.plusDays(random.nextInt(5) - 2);
      var endRnd = end.plusDays(random.nextInt(5) - 2);
      var booker =
          new DummyBooker(gate, latch, bookingRepository, roomAssigner, roomType, startRnd, endRnd);
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

    @Getter private final AtomicReference<Booking> successfulBooking = new AtomicReference<>();
    @Getter private final AtomicReference<Throwable> caughtException = new AtomicReference<>();

    public boolean isSuccessfullyBooked() {
      return successfulBooking.get() != null && caughtException.get() == null;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void run() {
      gate.await();
      try {
        successfulBooking.set(createBooking());
      } catch (Exception ex) {
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
