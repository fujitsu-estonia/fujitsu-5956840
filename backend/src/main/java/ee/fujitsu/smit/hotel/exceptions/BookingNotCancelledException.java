package ee.fujitsu.smit.hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ee.fujitsu.smit.hotel.tools.Constants.ERROR_BOOKING_NOT_CANCELLED;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BookingNotCancelledException extends RuntimeException {

  public BookingNotCancelledException() {
    super(ERROR_BOOKING_NOT_CANCELLED);
  }

}
