package ee.fujitsu.smit.hotel.tools.booking;

import ee.fujitsu.smit.hotel.configs.BusinessLogicPropertiesConfig;
import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.app_properties.BusinessLogicProperties;
import ee.fujitsu.smit.hotel.app_properties.dto.Time;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Converts booking {@link LocalDate dates} from API DTOs to {@link LocalDateTime date&times} for
 * {@link Booking booking entity}, using the default check-in and check-out times defined in {@link
 * BusinessLogicPropertiesConfig}
 */
@Component
public class BookingDatesConverter {

  private final Time checkInTime;
  private final Time checkOutTime;

  public BookingDatesConverter(BusinessLogicProperties appProperties) {
    checkInTime = appProperties.booking().defaultCheckInTime();
    checkOutTime = appProperties.booking().defaultCheckOutTime();
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
