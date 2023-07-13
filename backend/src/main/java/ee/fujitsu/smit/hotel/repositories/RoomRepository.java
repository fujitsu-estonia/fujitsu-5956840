package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  /**
   * Query will select all rooms of provided type in hotel, and filter those rooms, which are
   * booked<sup>1</sup> on given period (fully or partially overlapping), giving the available rooms
   * in result .
   *
   * <p><sup>1</sup> - booked room means that on the given period there exists a booking for
   * provided room type with one of the following booking statuses [{@link BookingStatus#ACCEPTED},
   * {@link BookingStatus#STARTED}] (those bookings are counted)
   *
   * @param roomType room type
   * @param startDate booking period start date
   * @param endDate booking period end date
   * @return list of available (not booked) rooms of given type on given period
   */
  @Query(
      value =
          """
      SELECT r
      FROM Room r
      WHERE r.roomType = :roomType
      AND NOT EXISTS (
      SELECT b
      FROM Booking b
      WHERE b.roomType = :roomType
        AND b.assignedRoom = r
        AND b.status < 2
        AND b.startDate <= :endDate AND b.endDate >= :startDate)
      """)
  @Transactional(readOnly = true)
  List<Room> findAvailableRoomsOfTypeByPeriod(
      RoomType roomType, LocalDateTime startDate, LocalDateTime endDate);
}
