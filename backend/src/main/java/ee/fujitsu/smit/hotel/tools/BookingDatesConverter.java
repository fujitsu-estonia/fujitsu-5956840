package ee.fujitsu.smit.hotel.tools;

import ee.fujitsu.smit.hotel.configs.HotelConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Converts booking {@link LocalDate dates} from API DTOs to {@link LocalDateTime date&times} for
 * {@link ee.fujitsu.smit.hotel.entities.Booking booking entity}, using the default check-in and
 * check-out times defined in {@link HotelConfig}
 */
@Component
public class BookingDatesConverter {

  private final HotelConfig.Time checkInTime;
  private final HotelConfig.Time checkOutTime;

  public BookingDatesConverter(HotelConfig hotelConfig) {
    checkInTime = hotelConfig.booking().getDefaultCheckInTime();
    checkOutTime = hotelConfig.booking().getDefaultCheckOutTime();
  }

  public LocalDateTime mapBookingStartTime(LocalDate requestedStartTime) {
    if (requestedStartTime == null) {
      return null;
    }
    return requestedStartTime.atTime(checkInTime.hour(), checkInTime.minute(), 0);
  }

  public LocalDateTime mapBookingEndTime(LocalDate requestedEndTime) {
    if (requestedEndTime == null) {
      return null;
    }
    return requestedEndTime.atTime(checkOutTime.hour(), checkOutTime.minute(), 0);
  }
}
