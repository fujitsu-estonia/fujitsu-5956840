package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  //findByStartDateBeforeLessThaneEnd(statdate, enddate)
}
