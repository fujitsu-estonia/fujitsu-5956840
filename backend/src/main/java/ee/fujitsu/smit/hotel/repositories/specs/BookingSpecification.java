package ee.fujitsu.smit.hotel.repositories.specs;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/** {@link Specification JPA Specifications} for {@link Booking} entity. */
public interface BookingSpecification extends Specification<Booking> {

  /**
   * Returns conjunction predicate (always true) if startTime not provided or predicate to select
   * bookings that start after (or at the same time) the provided startTime.
   *
   * @param startTime from what time search bookings (nullable)
   * @return {@link BookingSpecification}
   */
  static BookingSpecification startsAfterIfProvided(@Nullable LocalDate startTime) {
    return (booking, cq, cb) ->
        startTime == null
            ? cb.conjunction()
            : cb.greaterThanOrEqualTo(booking.get("startDate"), startTime);
  }

  /**
   * Returns conjunction predicate (always true) if endTime not provided or predicate to select
   * bookings that end before (or at the same time) the provided endTime.
   *
   * @param endTime from what time search bookings (nullable)
   * @return {@link BookingSpecification}
   */
  static BookingSpecification endsBeforeIfProvided(@Nullable LocalDate endTime) {
    return (booking, cq, cb) ->
        endTime == null ? cb.conjunction() : cb.lessThanOrEqualTo(booking.get("endDate"), endTime);
  }

  /**
   * Returns conjunction predicate (always true) if status not provided or predicate to select
   * bookings that have given status
   *
   * @param status booking status
   * @return {@link BookingSpecification}
   */
  static BookingSpecification withStatusIfProvided(@Nullable BookingStatus status) {
    return (booking, cq, cb) ->
        status == null ? cb.conjunction() : cb.equal(booking.get("status"), status.ordinal());
  }
}
