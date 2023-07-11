package ee.fujitsu.smit.hotel.controllers.aop;

import ee.fujitsu.smit.hotel.exceptions.BadRequestException;
import ee.fujitsu.smit.hotel.models.ErrorsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorsDto> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .toList();
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ErrorsDto> handleBadRequestExceptions(
      BadRequestException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorsDto> handleGeneralExceptions(Exception ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors),
        new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR
    );
  }

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<ErrorsDto> handleRuntimeExceptions(
      RuntimeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ErrorsDto getErrorsMap(List<String> errors) {
    var errorResponse = new ErrorsDto();
    errorResponse.setErrors(errors);
    return errorResponse;
  }
}
