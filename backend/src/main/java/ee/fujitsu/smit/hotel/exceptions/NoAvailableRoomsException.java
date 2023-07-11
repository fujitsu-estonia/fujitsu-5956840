package ee.fujitsu.smit.hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_NO_ROOMS_AVAILABLE_FOR_BOOKING;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoAvailableRoomsException extends BadRequestException {

  public NoAvailableRoomsException(String roomType) {
    super(ERROR_NO_ROOMS_AVAILABLE_FOR_BOOKING + '(' + roomType + ')');
  }

}
