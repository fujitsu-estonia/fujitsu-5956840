package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.repositories.models.RoomTypeExtended;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

  /**
   * Query will select all room types in hotel by given beds count and adds information about the amount of all
   * such rooms and how many of them are booked<sup>1</sup> on given period (fully or partially overlapping).
   *
   * <p><sup>1</sup> - booked room means that on the given period there exists a booking for
   * provided room type with one of the following booking statuses [{@link BookingStatus#ACCEPTED},
   * {@link BookingStatus#STARTED}] (those bookings are counted)
   *
   * @param bedsCount beds count
   * @param startDate booking period start date
   * @param endDate booking period end date
   * @return list of room types (extended with all rooms count and booked rooms count) of given beds count on given period
   */
  @Query(
      nativeQuery = true,
      value =
          """
      SELECT
        room_type.id as roomTypeId,
        room_type.title,
        room_type.description,
        room_type.beds_count as bedsCount,
        room_type.price_per_night as pricePerNight,
        room_type.preview_picture_url as previewPictureUrl,
        all_rooms as allRooms,
        booking.booked_rooms as bookedRooms
      FROM (
        SELECT
          room.room_type_id as id,
          room_type.title,
          room_type.description,
          room_type.beds_count,
          room_type.price_per_night,
          room_type.preview_picture_url,
          COUNT(room.room_type_id) as all_rooms
        FROM room
        INNER JOIN room_type ON room_type.id=room.room_type_id
        WHERE beds_count = :bedsCount OR :bedsCount IS NULL
        GROUP BY room_type_id
      ) room_type
      LEFT JOIN (
        SELECT
          room_type_id,
          COUNT(room_type_id) as booked_rooms
        FROM booking
        WHERE booking.status < 2
          AND start_date <= :endDate AND end_date >= :startDate
        GROUP BY room_type_id
      ) AS booking ON room_type.id=booking.room_type_id""")
  List<RoomTypeExtended> getAvailableRoomTypesByBedsCountForPeriod(
      Integer bedsCount, LocalDate startDate, LocalDate endDate);
}
