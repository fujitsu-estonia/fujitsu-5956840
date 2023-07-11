package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.SearchRoomDto;
import ee.fujitsu.smit.hotel.repositories.models.RoomTypeExtended;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

  @Mapping(target = "id", ignore = true)
  RoomType mapToEntity(SearchRoomDto searchRoomDto);

  @Mapping(target = "roomTypeId", source = "id")
  RoomDetailsDto mapToDto(RoomType entity);

  @Mapping(target = "description", source = "descriptionStr")
  RoomDetailsDto mapToDto(RoomTypeExtended entity);
}
