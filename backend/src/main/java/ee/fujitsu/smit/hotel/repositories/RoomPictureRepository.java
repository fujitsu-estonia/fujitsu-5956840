package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.RoomPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPictureRepository extends JpaRepository<RoomPicture, Long> {}
