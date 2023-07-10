package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.enums.RequestOriginator;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import static ee.fujitsu.smit.hotel.tools.constants.SchemaConstants.DEFAULT_UUID;
import static ee.fujitsu.smit.hotel.tools.constants.WebConstants.X_ORIGINATOR_HEADER;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  @Operation(description = "Book hotel room request")
  public @Schema(type = "string", format = "uuid", description = "Requested booking id") UUID
      bookRoom(@RequestBody @Valid CreateBookingRequestDto bookRoomRequest) {
    return bookingService.createBooking(bookRoomRequest);
  }

  @PostMapping("cancel/{bookingId}")
  @Operation(description = "Cancel booking. 200 OK if cancellation successful.")
  public void cancelBooking(
      @Schema(type = "string", format = "uuid", description = "Booking id", example = DEFAULT_UUID)
          @PathVariable
          UUID bookingId,
      @Schema(
              description =
                  "Dummy identity of user, who made the api request. "
                      + "Optional, but if you want to cancel booking as admin, set 'ADMIN' value")
          @RequestHeader(name = X_ORIGINATOR_HEADER, required = false, defaultValue = "USER")
          RequestOriginator requestOriginator) {
    bookingService.cancelBooking(bookingId, requestOriginator == RequestOriginator.USER);
  }

  @GetMapping("{bookingId}")
  @Operation(description = "Get booking")
  public BookingDetailsDto getBookingDetails(
      @Schema(type = "string", format = "uuid", description = "Booking id", example = DEFAULT_UUID)
          @PathVariable
          UUID bookingId) {
    return bookingService.getBooking(bookingId);
  }

  @PostMapping("search")
  @Operation(description = "Get all active bookings")
  public List<BookingDetailsDto> searchBookings(@RequestBody SearchBookingsDto searchBookingsDto) {
    return bookingService.findBookings(searchBookingsDto);
  }
}
