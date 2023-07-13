package ee.fujitsu.smit.hotel.exceptions.booking;

import ee.fujitsu.smit.hotel.exceptions.BadRequestException;

import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_BOOKING_CANCELLATION_DEADLINE_EXCEEDED;

public class BookingCancellationDeadlineExceededException extends BadRequestException {
  public BookingCancellationDeadlineExceededException() {
    super(ERROR_BOOKING_CANCELLATION_DEADLINE_EXCEEDED);
  }
}
