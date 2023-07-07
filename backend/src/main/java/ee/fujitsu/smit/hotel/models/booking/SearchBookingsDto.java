package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchBookingsDto {

  @Nullable
  @Schema(
      implementation = BookingStatus.class,
      description = "Search bookings with given status only",
      nullable = true)
  private BookingStatus bookingStatus;

  @Nullable
  @Schema(
      type = "string",
      format = "date",
      description = "Search bookings from date",
      nullable = true)
  private LocalDate fromDate;

  @Nullable
  @Schema(
      type = "string",
      format = "date",
      description = "Search bookings up to date",
      nullable = true)
  private LocalDate toDate;
}
