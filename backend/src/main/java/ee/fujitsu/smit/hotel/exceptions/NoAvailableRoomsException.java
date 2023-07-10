package ee.fujitsu.smit.hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoAvailableRoomsException extends BadRequestException {

  public NoAvailableRoomsException(String roomType) {
    super(String.format("No rooms of type '%s' available for booking", roomType));
  }

}
