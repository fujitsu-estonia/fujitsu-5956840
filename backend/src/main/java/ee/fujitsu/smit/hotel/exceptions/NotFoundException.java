package ee.fujitsu.smit.hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ee.fujitsu.smit.hotel.tools.Constants.NOT_FOUND_CODE;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super(NOT_FOUND_CODE);
  }

  public NotFoundException(String details) {
    super(NOT_FOUND_CODE + "." + details);
  }
}
