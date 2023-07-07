package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.entities.RoomType;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.models.booking.BookedRoomDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.BookingTimeConverter;
import lombok.Setter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {

  @Autowired @Setter private RoomTypeRepository roomTypeRepository;
  @Autowired @Setter protected BookingTimeConverter bookingTimeConverter;

  @Mapping(target = "startDate", expression = "java( bookingTimeConverter.mapBookingStartTime(dto.getBookingPeriod().startDate()) )")
  @Mapping(target = "endDate", expression = "java( bookingTimeConverter.mapBookingEndTime(dto.getBookingPeriod().endDate()) )")
  @Mapping(target = "roomType", expression = "java( findRoomTypeById(dto.getRoomTypeId()) )")
  @Mapping(target = "firstName", source = "personData.firstName")
  @Mapping(target = "lastName", source = "personData.lastName")
  @Mapping(target = "idCode", source = "personData.idCode")
  @Mapping(target = "email", source = "personData.email")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "priceTotal", ignore = true)
  @Mapping(target = "assignedRoom", ignore = true)
  public abstract Booking mapToEntity(CreateBookingRequestDto dto);

  @AfterMapping
  protected void calculatePriceTotal(@MappingTarget Booking entity, CreateBookingRequestDto dto) {
    entity.setPriceTotal(
        entity.getRoomType().getPricePerNight() * dto.getBookingPeriod().durationInDays());
  }

  @Mapping(target = "roomDetails", expression = "java( createBookedRoomDetails(entity) )")
  @Mapping(target = "personData", expression = "java( createPersonalData(entity) )")
  public abstract BookingDetailsDto mapToDto(Booking entity);

  protected RoomType findRoomTypeById(long id) {
    return roomTypeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("roomType(" + id + ')'));
  }

  protected BookedRoomDetailsDto createBookedRoomDetails(Booking booking) {
    var dto = new BookedRoomDetailsDto();
    if (booking.getRoomType() != null) {
      dto.setTitle(booking.getRoomType().getTitle());
      dto.setDescription(booking.getRoomType().getDescription());
      dto.setBedsCount(booking.getRoomType().getBedsCount());
      dto.setPricePerNight(booking.getRoomType().getPricePerNight());
      dto.setPreviewPictureUrl(booking.getRoomType().getPreviewPictureUrl());
    }
    if (booking.getAssignedRoom() != null) {
      dto.setRoomNumber(booking.getAssignedRoom().getRoomNumber());
    }
    return dto;
  }

  protected PersonData createPersonalData(Booking booking) {
    var dto = new PersonData();
    dto.setFirstName(booking.getFirstName());
    dto.setLastName(booking.getLastName());
    dto.setIdCode(booking.getIdCode());
    dto.setEmail(booking.getEmail());
    return dto;
  }
}
