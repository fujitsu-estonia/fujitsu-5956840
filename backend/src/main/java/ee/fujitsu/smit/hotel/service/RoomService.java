package ee.fujitsu.smit.hotel.service;

import ee.fujitsu.smit.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService extends JpaRepository<Room, Long> {

    List<Room> findByRoomNumber(Integer roomNumber);
}
