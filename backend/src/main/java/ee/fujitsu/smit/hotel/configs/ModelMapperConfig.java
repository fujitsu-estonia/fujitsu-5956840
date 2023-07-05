package ee.fujitsu.smit.hotel.configs;

import ee.fujitsu.smit.hotel.entities.Room;
import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ModelMapperConfig {

  /**
   * Model mapper configuration
   *
   * @return ModelMapper
   */
  @Bean
  public ModelMapper mapper() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    modelMapper.getConfiguration()
        .setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
    addRoomToRoomDetailsDtoMapping(modelMapper);
    addCreateUpdateRoomRequestDtoToRoom(modelMapper);
    return modelMapper;
  }

  private void addRoomToRoomDetailsDtoMapping(final ModelMapper modelMapper) {
    logMapping(Room.class, RoomDetailsDto.class);
    modelMapper.typeMap(Room.class, RoomDetailsDto.class);
  }

  private void addCreateUpdateRoomRequestDtoToRoom(final ModelMapper modelMapper) {
    logMapping(CreateUpdateRoomRequestDto.class, Room.class);
    modelMapper.typeMap(CreateUpdateRoomRequestDto.class, Room.class).addMappings(
        mapper -> {
          mapper.skip(CreateUpdateRoomRequestDto::getId, Room::setId);
          mapper.map(CreateUpdateRoomRequestDto::getRoomNumber, Room::setRoomNumber);
          mapper.map(CreateUpdateRoomRequestDto::getBeds, Room::setBeds);
          mapper.map(CreateUpdateRoomRequestDto::getDescription, Room::setDescription);
        }
    );
  }

  private void logMapping(final Class<?> origin, final Class<?> destination) {
    log.info("mapping {} to {}", origin.getName(), destination.getName());
  }
}
