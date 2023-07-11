package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.enums.RequestOriginator;
import ee.fujitsu.smit.hotel.models.ErrorsDto;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.services.BookingService;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  @Operation(
      summary = "Book a room",
      description = "Book hotel room request",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Create booking request data",
              required = true,
              content =
                  @Content(
                      mediaType = APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = CreateBookingRequestDto.class))),
      responses = {
        @ApiResponse(
            description = "Room was booked",
            responseCode = "200",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(
                            description = "Created booking id",
                            type = "string",
                            format = "uuid"))),
        @ApiResponse(
            description = "No rooms were available for booking",
            responseCode = "400",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorsDto.class)))
      })
  public UUID bookRoom(@RequestBody @Valid CreateBookingRequestDto bookRoomRequest) {

    return bookingService.createBooking(bookRoomRequest);
  }

  @PostMapping("cancel/{bookingId}")
  @Operation(
      summary = "Cancel booking",
      description = "Cancel booking by id",
      parameters = {
        @Parameter(
            name = "bookingId",
            in = ParameterIn.PATH,
            description = "Id of booking to be cancelled",
            required = true,
            example = DEFAULT_UUID,
            schema = @Schema(implementation = UUID.class)),
        @Parameter(
            name = X_ORIGINATOR_HEADER,
            in = ParameterIn.HEADER,
            description =
                "Dummy identity of user, who made the api request. Optional, but if you want to cancel booking as admin, set 'ADMIN' value",
            allowEmptyValue = true)
      },
      responses = {
        @ApiResponse(description = "Booking was cancelled", responseCode = "200"),
        @ApiResponse(
            description =
                "Requested booking not found. Error code: '"
                    + Constants.NOT_FOUND_CODE
                    + "'.\t\nBooking was already cancelled. Error code: '"
                    + Constants.ERROR_BOOKING_ALREADY_CANCELLED
                    + "'.\t\nBooking cancellation deadline was exceeded. Error code: '"
                    + Constants.ERROR_BOOKING_CANCELLATION_DEADLINE_EXCEEDED
                    + "'.",
            responseCode = "400",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorsDto.class))),
        @ApiResponse(
            description =
                "Booking could not be cancelled for unknown reason. Error code: '"
                    + Constants.ERROR_BOOKING_NOT_CANCELLED
                    + "'",
            responseCode = "500",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorsDto.class)))
      })
  public boolean cancelBooking(
      @PathVariable UUID bookingId,
      @RequestHeader(name = X_ORIGINATOR_HEADER, required = false, defaultValue = "USER")
          RequestOriginator requestOriginator) {

    return bookingService.cancelBooking(bookingId, requestOriginator == RequestOriginator.USER);
  }

  @GetMapping("{bookingId}")
  @Operation(
      summary = "Get booking",
      description = "Find single booking by id",
      parameters =
          @Parameter(
              name = "bookingId",
              in = ParameterIn.PATH,
              description = "Id of booking to be found",
              required = true,
              example = DEFAULT_UUID,
              schema = @Schema(implementation = UUID.class)),
      responses = {
        @ApiResponse(
            description = "Booking successfully found",
            responseCode = "200",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(
                            description = "Found booking data",
                            implementation = BookingDetailsDto.class))),
        @ApiResponse(
            description = "Booking not found",
            responseCode = "400",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorsDto.class)))
      })
  public BookingDetailsDto getBookingDetails(@PathVariable UUID bookingId) {

    return bookingService.getBooking(bookingId);
  }

  @PostMapping("search")
  @Operation(
      summary = "Search for bookings (admin method)",
      description = "Get bookings by search parameters",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "search parameters",
              required = true,
              content =
                  @Content(
                      mediaType = APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = SearchBookingsDto.class))),
      responses =
          @ApiResponse(
              description = "Bookings search results",
              responseCode = "200",
              content =
                  @Content(
                      mediaType = APPLICATION_JSON_VALUE,
                      array =
                          @ArraySchema(
                              arraySchema = @Schema(description = "found bookings list"),
                              schema = @Schema(implementation = BookingDetailsDto.class)))))
  public List<BookingDetailsDto> searchBookings(@RequestBody SearchBookingsDto searchBookingsDto) {
    return bookingService.findBookings(searchBookingsDto);
  }
}
