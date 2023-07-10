package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.Booking;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Condition;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Conditions {

  static Condition<Booking> id(UUID testId) {
    return new Condition<>(booking -> booking.getId().equals(testId), "id %s", testId);
  }

}
