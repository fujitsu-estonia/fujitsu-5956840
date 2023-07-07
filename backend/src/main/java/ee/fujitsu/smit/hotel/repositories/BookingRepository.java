package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.repositories.specs.BookingSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository
    extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {

  default List<Booking> findByStatusAndTimeBounds(SearchBookingsDto searchBookings) {
    return findAll(
        BookingSpecification.withStatusIfProvided(searchBookings.getBookingStatus())
            .and(
                BookingSpecification.startsAfterIfProvided(searchBookings.getFromDate())
                    .and(BookingSpecification.endsBeforeIfProvided(searchBookings.getToDate()))));
  }

  /**
   * Query will count the total amount of rooms of provided type in hotel, count the rooms of the
   * same type, that are booked<sup>1</sup> on given period (fully or partially overlapping), and
   * select only the difference (all - booked), a.k.a. available rooms.
   *
   * <p><sup>1</sup> - booked room means that on the given period there exists a booking for
   * provided room type with one of the following booking statuses [{@link BookingStatus#ACCEPTED},
   * {@link BookingStatus#STARTED}] (those bookings are counted)
   *
   * @implNote Works with H2 database. Might not be working with some other DBs
   * @param roomTypeId room type id
   * @param startTime booking period start date
   * @param endTime booking period end date
   * @return number of available (not booked) rooms of given type on given period
   */
  @Query(
      nativeQuery = true,
      value =
          "SELECT "
              + "(SELECT COUNT(*) FROM room WHERE room_type_id = :roomTypeId)"
              + " - "
              + "(SELECT COUNT(*) FROM booking WHERE room_type_id = :roomTypeId "
              + "AND status < 2 " // ACCEPTED or STARTED
              + "AND start_date <= :endTime AND end_date >= :startTime)")
  long countAvailableRoomsOfTypeForPeriod(
      Long roomTypeId, LocalDateTime startTime, LocalDateTime endTime);

  default long countAvailableRoomsOfTypeForPeriod(Booking booking) {
    return countAvailableRoomsOfTypeForPeriod(
        booking.getRoomType().getId(), booking.getStartDate(), booking.getEndDate());
  }
}
