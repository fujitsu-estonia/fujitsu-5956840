package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.models.BookingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

  @PostMapping
  @Operation(description = "Book room")
  public @Schema(type = "string", format = "uuid", description = "Booking id") String bookRoom(@RequestBody BookingDto bookRoomRequest) {
    throw new NotImplementedException();
  }

  @PostMapping("cancel/{bookingId}")
  @Operation(description = "Cancel booking")
  public boolean invalidateBooking(
      @PathVariable @Schema(type = "string", format = "uuid", description = "Booking id", example = "2bde2f0c-f97e-4beb-92ab-7477dc7952f5")
          UUID bookingId) {
    throw new NotImplementedException();
  }

  @GetMapping("{bookingId}")
  @Operation(description = "Get booking")
  public BookingDto getBooking(@PathVariable  @Schema(type = "string", format = "uuid", description = "Booking id", example = "2bde2f0c-f97e-4beb-92ab-7477dc7952f5") UUID bookingId) {
    throw new NotImplementedException();
  }

  @GetMapping
  @Operation(description = "Get all active bookings")
  public List<BookingDto> getActiveBookings() {
    throw new NotImplementedException();
  }
}
