package ee.fujitsu.smit.hotel.tools.booking;

import ee.fujitsu.smit.hotel.configs.BusinessLogicPropertiesConfig;
import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.app_properties.BusinessLogicProperties;
import ee.fujitsu.smit.hotel.app_properties.dto.Time;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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

  public LocalDateTime getDefaultCheckInTime(LocalDate requestedStartTime) {
    return Optional.ofNullable(requestedStartTime)
        .map(date -> dateTime(date, checkInTime))
        .orElse(null);
  }

  public LocalDateTime getDefaultCheckOutTime(LocalDate requestedEndTime) {
    return Optional.ofNullable(requestedEndTime)
        .map(date -> dateTime(date, checkOutTime))
        .orElse(null);
  }

  private LocalDateTime dateTime(LocalDate date, Time time) {
    return date.atTime(time.hour(), time.minute(), 0);
  }
}
