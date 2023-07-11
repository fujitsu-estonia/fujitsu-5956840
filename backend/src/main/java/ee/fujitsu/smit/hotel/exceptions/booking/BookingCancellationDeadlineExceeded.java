package ee.fujitsu.smit.hotel.exceptions.booking;

import ee.fujitsu.smit.hotel.exceptions.BadRequestException;

import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_BOOKING_CANCELLATION_DEADLINE_EXCEEDED;

public class BookingCancellationDeadlineExceeded extends BadRequestException {
  public BookingCancellationDeadlineExceeded() {
    super(ERROR_BOOKING_CANCELLATION_DEADLINE_EXCEEDED);
  }
}
