package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  //findByStartDateBeforeLessThaneEnd(statdate, enddate)
}
