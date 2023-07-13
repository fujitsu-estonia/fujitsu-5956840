package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Booking_;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.repositories.specs.BookingSpecification;
import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository
    extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {

  /**
   * Finds bookings by the given search parameters. All search filter parameters are nullable and if
   * not provided (null) then parameter is ignored. Result will be sorted by the provided search
   * order parameters, or by {@link Booking_#CREATED_AT CREATED_AT} by default.
   *
   * @param searchBookings bookings search parameters
   * @return found bookings
   */
  default List<Booking> findByStatusAndTimeBounds(@NonNull SearchBookingsDto searchBookings) {
    var searchParams =
        Optional.ofNullable(searchBookings.getFilterBy()).orElse(SearchBookingsDto.EMPTY_FILTER);
    var sort =
        Sort.by(
            Optional.ofNullable(searchBookings.getOrderBy())
                .orElse(SearchBookingsDto.DEFAULT_ORDER_BY)
                .stream()
                .map(order -> new Sort.Order(order.getDirection(), order.getName()))
                .toList());
    return findAll(
        BookingSpecification.withOneOfStatusesIfProvided(searchParams.getBookingStatuses())
            .and(
                BookingSpecification.startsAfterIfProvided(searchParams.getFromDate())
                    .and(BookingSpecification.endsBeforeIfProvided(searchParams.getToDate()))),
        sort);
  }
}
