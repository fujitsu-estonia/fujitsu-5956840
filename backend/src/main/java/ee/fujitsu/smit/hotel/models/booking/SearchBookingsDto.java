package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SearchBookingsDto {

  @Nullable
  @Singular
  @Schema(
      implementation = BookingStatus.class,
      description = "Search bookings with given statuses only",
      nullable = true)
  private List<BookingStatus> bookingStatuses;

  @Nullable
  @Schema(
      type = "string",
      format = "date",
      description = "Search bookings from date (inclusive)",
      nullable = true)
  private LocalDate fromDate;

  @Nullable
  @Schema(
      type = "string",
      format = "date",
      description = "Search bookings up to date (exclusive)",
      nullable = true)
  private LocalDate toDate;
}
