package ee.fujitsu.smit.hotel.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class BadRequestException extends RuntimeException {

  protected BadRequestException(String msg) {
    super(msg);
  }

}
