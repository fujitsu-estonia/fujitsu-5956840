package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingAlreadyCancelledException;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingNotCancelledException;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface BookingService {

  /**
   * Create new booking with status {@link BookingStatus#ACCEPTED ACCEPTED}, if there are available
   * rooms of required type for given booking period
   *
   * @param createBookingRequestDto create booking request data
   * @return created booking id
   * @throws ee.fujitsu.smit.hotel.exceptions.NoAvailableRoomsException if there is no room
   *     available for requested booking period
   */
  UUID createBooking(CreateBookingRequestDto createBookingRequestDto);

  /**
   * Set booking status as {@link BookingStatus#isCancelled() cancelled}.
   *
   * @param bookingId id of booking to be cancelled
   * @param cancelAsUser identifies, who cancels the booking (user or admin)
   * @throws ee.fujitsu.smit.hotel.exceptions.NotFoundException if no booking can be found by given
   *     {@code bookingId}
   * @throws BookingAlreadyCancelledException if given booking was
   *     already cancelled
   * @throws BookingNotCancelledException if any other (unexpected)
   *     problem happens on booking cancellation
   */
  boolean cancelBooking(UUID bookingId, boolean cancelAsUser);

  /**
   * Get booking by id.
   *
   * @param bookingId booking id
   * @return founds booking or {@code null} if nothing was found
   */
  BookingDetailsDto getBooking(UUID bookingId);

  /**
   * Find bookings that match given {@link SearchBookingsDto searhc parameters}
   *
   * @param searchBookings bookings search parameters
   * @return found bookings list
   */
  @NonNull List<BookingDetailsDto> findBookings(@NonNull SearchBookingsDto searchBookings);
}
