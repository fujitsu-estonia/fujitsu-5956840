package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.entities.RoomType;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.BookingDto;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {

  @Autowired @Getter @Setter private RoomTypeRepository roomTypeRepository;

  @Mapping(target = "startDate", source = "bookingPeriod.startDate")
  @Mapping(target = "endDate", source = "bookingPeriod.endDate")
  @Mapping(target = "roomType", expression = "java( findRoomTypeById(dto.getRoomTypeId()) )")
  @Mapping(target = "assignedRoom", ignore = true)
  @Mapping(target = "status", ignore = true)
  public abstract Booking mapToEntity(BookingDto dto);

  @Mapping(target = "bookingPeriod", expression = "java( new DateRange(entity.getStartDate(), entity.getEndDate()) )")
  public abstract BookingDto mapToDto(Booking entity);


  protected RoomType findRoomTypeById(long id) {
    return roomTypeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("roomType(" + id + ')'));
  }
}
