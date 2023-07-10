package ee.fujitsu.smit.hotel.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:hotel-properties.yaml", factory = YamlPropertySourceFactory.class)
public record HotelConfig(Booking booking) {

  @Component
  @Getter
  public static class Booking {

    private final Time defaultCheckInTime;
    private final Time defaultCheckOutTime;

    protected Booking(
        @Value("${booking.defaults.check-in.hour}") int defaultCheckInHour,
        @Value("${booking.defaults.check-in.minute}") int defaultCheckInMinute,
        @Value("${booking.defaults.check-out.hour}") int defaultCheckOutHour,
        @Value("${booking.defaults.check-out.minute}") int defaultCheckOutMinute) {
      defaultCheckInTime = new Time(defaultCheckInHour, defaultCheckInMinute);
      defaultCheckOutTime = new Time(defaultCheckOutHour, defaultCheckOutMinute);
    }
  }

  public record Time(int hour, int minute) {}
}
