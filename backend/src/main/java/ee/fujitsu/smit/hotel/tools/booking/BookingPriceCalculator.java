package ee.fujitsu.smit.hotel.tools.booking;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.models.booking.DateRange;
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
    return calculateBookingPrice(roomType, bookingPeriod.durationInDays());
  }

  public Double calculateBookingPrice(
      @NonNull RoomType roomType, long durationInDays) {
    return roomType.getPricePerNight() * durationInDays;
  }
}
