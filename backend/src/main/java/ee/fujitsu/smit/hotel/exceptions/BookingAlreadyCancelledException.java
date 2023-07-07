package ee.fujitsu.smit.hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ee.fujitsu.smit.hotel.tools.Constants.ERROR_BOOKING_ALREADY_CANCELLED;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingAlreadyCancelledException extends RuntimeException {

  public BookingAlreadyCancelledException() {
    super(ERROR_BOOKING_ALREADY_CANCELLED);
  }

}
