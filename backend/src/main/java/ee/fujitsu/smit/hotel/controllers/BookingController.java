package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.constants.RequestOriginator;
import ee.fujitsu.smit.hotel.models.BookingDto;
import ee.fujitsu.smit.hotel.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  @Operation(description = "Book hotel room request")
  public @Schema(type = "string", format = "uuid", description = "Requested booking id") UUID
      bookRoom(@RequestBody @Valid BookingDto bookRoomRequest) {
    return bookingService.createBooking(bookRoomRequest);
  }

  @PostMapping("cancel/{bookingId}")
  @Operation(description = "Cancel booking. 200 OK if cancellation successful.")
  public void cancelBooking(
      @Schema(type = "string", format = "uuid", description = "Booking id") @PathVariable
          UUID bookingId,
      @Schema(
              description =
                  "Dummy header to identify the identity of user, who made the api request")
          @RequestHeader(name = "X-Request-Originator", required = false, defaultValue = "USER")
          RequestOriginator requestOriginator) {
    bookingService.cancelBooking(bookingId, requestOriginator == RequestOriginator.USER);
  }

  @GetMapping("{bookingId}")
  @Operation(description = "Get booking")
  public BookingDto getBookingDetails(
      @Schema(type = "string", format = "uuid", description = "Booking id") @PathVariable
          UUID bookingId) {
    return bookingService.getBooking(bookingId);
  }

  @GetMapping
  @Operation(description = "Get all active bookings")
  public List<BookingDto> getActiveBookings() {
    throw new NotImplementedException();
  }
}
