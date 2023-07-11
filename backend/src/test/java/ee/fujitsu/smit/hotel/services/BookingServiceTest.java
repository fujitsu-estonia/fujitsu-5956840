package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingAlreadyCancelledException;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingNotCancelledException;
import ee.fujitsu.smit.hotel.exceptions.NoAvailableRoomsException;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.DateRange;
import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.services.impl.BookingServiceImpl;
import ee.fujitsu.smit.hotel.tools.mappers.BookingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookingServiceTest {

  @MockBean private BookingRepository bookingRepository;
  @MockBean private BookingMapper bookingMapper;

  private final long roomTypeId = 1;
  private BookingService bookingService;

  @BeforeEach
  void setUp() {
    var roomType = RoomType.builder().id(roomTypeId).bedsCount(1).title("roomType1").build();

    when(bookingMapper.mapToEntity(any(CreateBookingRequestDto.class)))
        .then(
            inv -> {
              var arg = inv.getArgument(0, CreateBookingRequestDto.class);

              var booking = new Booking();
              booking.setRoomType(roomType);
              booking.setStartDate(arg.getBookingPeriod().startDate().atTime(12, 0));
              booking.setEndDate(arg.getBookingPeriod().endDate().atTime(10, 0));
              booking.setFirstName(arg.getPersonData().getFirstName());
              booking.setLastName(arg.getPersonData().getLastName());
              booking.setEmail(arg.getPersonData().getEmail());
              return booking;
            });

    bookingService = new BookingServiceImpl(bookingRepository, bookingMapper);
  }

  @Test
  void createBooking_whenNoRoomsAvailable_throwsNoAvailableRoomsException() {
    doReturn(0L).when(bookingRepository).countAvailableRoomsOfTypeForPeriod(any(Booking.class));

    var createBookingRequest = createBookingReq();

    assertThrows(
        NoAvailableRoomsException.class, () -> bookingService.createBooking(createBookingRequest));
  }

  @Test
  void createBooking_newBookingGetsStatusAccepted() {
    doReturn(1L).when(bookingRepository).countAvailableRoomsOfTypeForPeriod(any(Booking.class));

    var testBookingId = UUID.randomUUID();
    var savedBookingRef = new AtomicReference<Booking>();
    when(bookingRepository.saveAndFlush(any(Booking.class)))
        .then(
            inv -> {
              var arg = inv.getArgument(0, Booking.class);
              savedBookingRef.set(arg);
              arg.setId(testBookingId);
              return arg;
            });

    var createBookingRequest = createBookingReq();
    var savedBookingId = bookingService.createBooking(createBookingRequest);

    assertEquals(testBookingId, savedBookingId);
    assertEquals(BookingStatus.ACCEPTED, savedBookingRef.get().getStatus());
  }

  @Test
  void cancelBooking_whenBookingNotFound_throwsNotFoundException() {
    var nonExistingBookingId = UUID.randomUUID();
    doReturn(Optional.empty()).when(bookingRepository).findById(nonExistingBookingId);

    assertThrows(
        NotFoundException.class, () -> bookingService.cancelBooking(nonExistingBookingId, true));
  }

  @Test
  void cancelBooking_whenBookingAlreadyCancelled_throwsBookingAlreadyCancelledException() {
    var cancelledBookingId = UUID.randomUUID();
    var booking = Booking.builder().status(BookingStatus.CANCELLED_BY_USER).id(cancelledBookingId);

    doAnswer(inv -> Optional.of(booking.build()))
        .when(bookingRepository)
        .findById(cancelledBookingId);

    assertThrows(
        BookingAlreadyCancelledException.class,
        () -> bookingService.cancelBooking(cancelledBookingId, true));

    booking.status(BookingStatus.CANCELLED_BY_ADMIN);

    assertThrows(
        BookingAlreadyCancelledException.class,
        () -> bookingService.cancelBooking(cancelledBookingId, true));
  }

  @Test
  void cancelBooking_whenUnexpectedError_throwsBookingNotCancelledException() {
    var bookingId = UUID.randomUUID();
    var booking = Booking.builder().id(bookingId).status(BookingStatus.ACCEPTED).build();

    doReturn(Optional.of(booking)).when(bookingRepository).findById(bookingId);
    doThrow(RuntimeException.class).when(bookingRepository).saveAndFlush(any(Booking.class));

    assertThrows(
        BookingNotCancelledException.class, () -> bookingService.cancelBooking(bookingId, true));
  }

  @ParameterizedTest
  @CsvSource({"true,CANCELLED_BY_USER", "false,CANCELLED_BY_ADMIN"})
  void cancelBooking_whenSuccessfullyCancelled_checkBookingCancellationStatus(
      boolean cancelAsUser, BookingStatus expectedStatus) {
    var bookingId = UUID.randomUUID();
    var booking = Booking.builder().id(bookingId).status(BookingStatus.ACCEPTED).build();
    var bookingUpdateRef = new AtomicReference<>(booking);

    doReturn(Optional.of(booking)).when(bookingRepository).findById(bookingId);
    doAnswer(
            inv -> {
              var arg = inv.getArgument(0, Booking.class);
              bookingUpdateRef.set(arg);
              return arg;
            })
        .when(bookingRepository)
        .saveAndFlush(any(Booking.class));

    bookingService.cancelBooking(bookingId, cancelAsUser);

    assertEquals(expectedStatus, bookingUpdateRef.get().getStatus());
  }

  @Test
  void getBooking_whenBookingNotFound_throwsNotFoundException() {
    var bookingId = UUID.randomUUID();

    doReturn(Optional.empty()).when(bookingRepository).findById(bookingId);

    assertThrows(NotFoundException.class, () -> bookingService.getBooking(bookingId));
  }

  @Test
  void findBookings() {
    var bookingId = UUID.randomUUID();
    var booking = Booking.builder().id(bookingId).build();

    var search =
        SearchBookingsDto.builder()
            .bookingStatus(BookingStatus.ACCEPTED)
            .fromDate(LocalDate.of(2023, 4, 4))
            .toDate(LocalDate.of(2023, 5, 5))
            .build();

    when(bookingRepository.findByStatusAndTimeBounds(search)).thenReturn(List.of(booking));

    var found = bookingService.findBookings(search);

    assertEquals(1, found.size());
  }

  private CreateBookingRequestDto createBookingReq() {
    var req = new CreateBookingRequestDto();
    req.setRoomTypeId(roomTypeId);
    req.setBookingPeriod(new DateRange(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 5)));

    var personData = new PersonData();
    personData.setFirstName("firstName");
    personData.setLastName("lastName");
    personData.setEmail("firstName.lastName@email");
    personData.setIgnoreIdCode(true);
    req.setPersonData(personData);

    return req;
  }
}
