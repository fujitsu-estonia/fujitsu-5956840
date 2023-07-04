package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
