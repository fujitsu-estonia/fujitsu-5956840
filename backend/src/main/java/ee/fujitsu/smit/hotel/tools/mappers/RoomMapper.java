package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.entities.Room;
import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

  @Mapping(target = "id", ignore = true)
  Room mapToEntity(CreateUpdateRoomRequestDto createUpdateRequest);

  RoomDetailsDto mapToDto(Room entity);

}
