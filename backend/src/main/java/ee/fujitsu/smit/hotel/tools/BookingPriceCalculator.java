package ee.fujitsu.smit.hotel.tools;

import ee.fujitsu.smit.hotel.entities.RoomType;
import ee.fujitsu.smit.hotel.models.DateRange;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class BookingPriceCalculator {

  /**
   * Simple calculation of booking price based on room type and booking period.
   *
   * @param roomType room type of booking
   * @param bookingPeriod booking start and end dates
   * @return calculated booking price
   */
  public Double calculateBookingPrice(
      @NonNull RoomType roomType, @NonNull DateRange bookingPeriod) {
    return roomType.getPricePerNight() * bookingPeriod.durationInDays();
  }
}
