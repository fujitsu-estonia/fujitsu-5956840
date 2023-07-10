package ee.fujitsu.smit.hotel.exceptions.booking;

import ee.fujitsu.smit.hotel.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_BOOKING_ALREADY_CANCELLED;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingAlreadyCancelledException extends BadRequestException {

  public BookingAlreadyCancelledException() {
    super(ERROR_BOOKING_ALREADY_CANCELLED);
  }
}
