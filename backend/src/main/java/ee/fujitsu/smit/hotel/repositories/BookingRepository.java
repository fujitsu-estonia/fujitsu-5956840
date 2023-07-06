package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

  //findByStartDateBeforeLessThaneEnd(statdate, enddate)
}
