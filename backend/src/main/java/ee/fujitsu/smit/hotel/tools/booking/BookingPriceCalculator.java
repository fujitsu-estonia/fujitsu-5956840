package ee.fujitsu.smit.hotel.tools.booking;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Component
public class BookingPriceCalculator {

  /**
   * @see BookingPriceCalculator#calculateBookingPrice(RoomType, Temporal, Temporal)
   */
  public Double calculateBookingPrice(@NonNull Booking booking) {
    return calculateBookingPrice(
        booking.getRoomType(), booking.getStartDate(), booking.getEndDate());
  }

  /**
   * Simple calculation of booking price based on booked room type and booking period (start and end
   * dates).
   *
   * @param roomType room type of booking
   * @param startTime booking start date
   * @param endTime booking end date
   * @return calculated booking price
   */
  public Double calculateBookingPrice(
      @NonNull RoomType roomType, @NonNull Temporal startTime, @NonNull Temporal endTime) {

    var bookingDurationInDays = ChronoUnit.DAYS.between(startTime, endTime);

    return roomType.getPricePerNight() * bookingDurationInDays;
  }
}
