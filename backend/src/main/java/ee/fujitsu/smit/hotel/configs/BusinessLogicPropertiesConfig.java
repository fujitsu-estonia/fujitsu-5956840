package ee.fujitsu.smit.hotel.configs;

import ee.fujitsu.smit.hotel.app_properties.BusinessLogicProperties;
import ee.fujitsu.smit.hotel.app_properties.dto.BookingsProperties;
import ee.fujitsu.smit.hotel.app_properties.dto.CancellationDeadline;
import ee.fujitsu.smit.hotel.app_properties.dto.Time;
import ee.fujitsu.smit.hotel.configs.helpers.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @see BusinessLogicProperties
 */
@Configuration
@PropertySource(
    value = "${app.businessLogic.properties:classpath:hotel-properties.yaml}",
    factory = YamlPropertySourceFactory.class)
public class BusinessLogicPropertiesConfig {

  @Value("${booking.cancellation-deadline.days-before-check-in:3}")
  private int cancellationDeadlineDaysBeforeCheckIn;

  @Value("${booking.defaults.check-in.hour:12}")
  private int defaultCheckInHour;

  @Value("${booking.defaults.check-in.minute:0}")
  private int defaultCheckInMinute;

  @Value("${booking.defaults.check-out.hour:10}")
  private int defaultCheckOutHour;

  @Value("${booking.defaults.check-out.minute:0}")
  private int defaultCheckOutMinute;

  @Bean
  public BusinessLogicProperties businessLogicProperties() {
    return new BusinessLogicProperties(bookingsProperties());
  }

  private BookingsProperties bookingsProperties() {
    return new BookingsProperties(
        new CancellationDeadline(cancellationDeadlineDaysBeforeCheckIn),
        new Time(defaultCheckInHour, defaultCheckInMinute),
        new Time(defaultCheckOutHour, defaultCheckOutMinute));
  }
}
