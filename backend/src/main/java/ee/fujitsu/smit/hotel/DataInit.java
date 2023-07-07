package ee.fujitsu.smit.hotel;

import ee.fujitsu.smit.hotel.entities.Room;
import ee.fujitsu.smit.hotel.entities.RoomType;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

  private final RoomTypeRepository roomTypeRepository;
  private final RoomRepository roomRepository;

  @Override
  public void run(String... args)  {
    var rt1 = roomTypeRepository.save(RoomType.builder().title("asd").description("asdasd").bedsCount(1).pricePerNight(13.5).build());
    roomRepository.save(Room.builder().roomType(rt1).roomNumber("A1").build());
    roomRepository.save(Room.builder().roomType(rt1).roomNumber("A2").build());
  }
}
