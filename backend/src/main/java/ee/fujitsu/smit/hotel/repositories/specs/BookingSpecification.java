package ee.fujitsu.smit.hotel.repositories.specs;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Booking_;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

/** {@link Specification JPA Specifications} for {@link Booking} entity. */
public interface BookingSpecification extends Specification<Booking> {

  /**
   * Returns conjunction predicate (always true) if startDate not provided or predicate to select
   * bookings that start after (or at the same time) the provided startDate.
   *
   * @param startDate from what time search bookings (nullable)
   * @return {@link BookingSpecification}
   */
  static BookingSpecification startsAfterIfProvided(@Nullable LocalDate startDate) {
    return (root, cq, cb) ->
        startDate == null
            ? cb.conjunction()
            : cb.greaterThanOrEqualTo(root.get(Booking_.startDate), startDate.atStartOfDay());
  }

  /**
   * Returns conjunction predicate (always true) if endDate not provided or predicate to select
   * bookings that end before the provided endDate.
   *
   * @param endDate from what time search bookings (nullable)
   * @return {@link BookingSpecification}
   */
  static BookingSpecification endsBeforeIfProvided(@Nullable LocalDate endDate) {
    return (root, cq, cb) ->
        endDate == null
            ? cb.conjunction()
            : cb.lessThanOrEqualTo(root.get(Booking_.endDate), endDate.atStartOfDay().minusSeconds(1));
  }

  /**
   * Returns conjunction predicate (always true) if statuses not provided or predicate to select
   * bookings that have given statuses
   *
   * @param statuses booking statuses
   * @return {@link BookingSpecification}
   */
  static BookingSpecification withOneOfStatusesIfProvided(@Nullable List<BookingStatus> statuses) {
    return (root, cq, cb) ->
        statuses == null || statuses.isEmpty()
            ? cb.conjunction()
            : root.get(Booking_.status).in(statuses.stream().map(BookingStatus::ordinal).toList());
  }
}
