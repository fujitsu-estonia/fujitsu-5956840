package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.models.room.RoomDetailsDto;
import ee.fujitsu.smit.hotel.repositories.models.RoomTypeExtended;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

  @Mapping(target = "roomTypeId", source = "id")
  RoomDetailsDto mapToDto(RoomType entity);

  @Mapping(target = "description", source = "descriptionStr")
  RoomDetailsDto mapToDto(RoomTypeExtended entity);
}
