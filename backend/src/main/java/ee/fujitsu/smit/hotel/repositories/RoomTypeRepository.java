package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {}
