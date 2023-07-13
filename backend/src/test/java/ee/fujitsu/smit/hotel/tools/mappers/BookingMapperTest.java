package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.DateRange;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.booking.BookingDatesConverter;
import ee.fujitsu.smit.hotel.tools.booking.BookingPriceCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class BookingMapperTest {

  @MockBean private RoomTypeRepository roomTypeRepository;

  @SpyBean private BookingPriceCalculator bookingPriceCalculator;

  @MockBean private BookingDatesConverter bookingDatesConverter;

  @Configuration
  static class Config {
    @Bean
    public BookingMapper bookingMapper() {
      return new BookingMapperImpl();
    }
  }

  @Autowired private BookingMapper mapper;

  @Test
  void calculatePriceTotal_delegatesPriceCalculationToCalculator() {
    var booking = new Booking();
    booking.setRoomType(RoomType.builder().pricePerNight(12.5).build());
    booking.setStartDate(LocalDate.now().atStartOfDay());
    booking.setEndDate(LocalDate.now().plusDays(4).atStartOfDay().minusMinutes(1));

    verify(bookingPriceCalculator, times(0)).calculateBookingPrice(any(Booking.class));

    mapper.calculatePriceTotal(booking);

    assertEquals(12.5 * 3, booking.getPriceTotal());
    verify(bookingPriceCalculator, times(1)).calculateBookingPrice(any(Booking.class));
  }

  @Test
  void findRoomTypeById_whenRoomTypeNotFound_throwsNotFoundException() {
    long roomTypeId = 1;

    when(roomTypeRepository.findById(roomTypeId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> mapper.findRoomTypeById(roomTypeId));
  }

  @Test
  void test_mapToEntity() {
    long roomTypeId = 1;
    var roomType = getRoomType(roomTypeId);
    when(roomTypeRepository.findById(roomTypeId)).thenReturn(Optional.of(roomType));

    var bookingPeriod = new DateRange(LocalDate.now(), LocalDate.now().plusDays(4));

    when(bookingDatesConverter.getDefaultCheckInTime(any(LocalDate.class)))
        .then(inv -> inv.getArgument(0, LocalDate.class).atTime(14, 0));
    when(bookingDatesConverter.getDefaultCheckOutTime(any(LocalDate.class)))
        .then(inv -> inv.getArgument(0, LocalDate.class).atTime(12, 0));

    var personalData =
        PersonData.builder()
            .firstName("firstName")
            .lastName("lastName")
            .email("firstName.lastName@email")
            .idCode("id_code")
            .build();

    var request = new CreateBookingRequestDto();
    request.setRoomTypeId(roomTypeId);
    request.setBookingPeriod(bookingPeriod);
    request.setPersonData(personalData);

    var result = mapper.mapToEntity(request);

    assertNotNull(result.getRoomType());
    assertEquals(roomTypeId, result.getRoomType().getId());
    assertEquals(roomType.getTitle(), result.getRoomType().getTitle());

    assertEquals(bookingPeriod.startDate(), result.getStartDate().toLocalDate());
    assertEquals(14, result.getStartDate().getHour());
    assertEquals(0, result.getStartDate().getMinute());

    assertEquals(bookingPeriod.endDate(), result.getEndDate().toLocalDate());
    assertEquals(12, result.getEndDate().getHour());
    assertEquals(0, result.getEndDate().getMinute());

    assertEquals(personalData.getFirstName(), result.getFirstName());
    assertEquals(personalData.getLastName(), result.getLastName());
    assertEquals(personalData.getEmail(), result.getEmail());
    assertEquals(personalData.getIdCode(), result.getIdCode());
  }

  @Test
  void test_mapToDto() {
    var roomType = getRoomType(4);
    var room = Room.builder().id(42L).roomType(roomType).roomNumber("404").roomNotes("notes").build();
    var booking = Booking.builder()
        .id(UUID.randomUUID())
        .roomType(roomType)
        .assignedRoom(room)
        .status(BookingStatus.STARTED)
        .startDate(LocalDate.now().minusDays(2).atTime(14,30))
        .endDate(LocalDate.now().plusDays(2).atTime(10, 0))
        .priceTotal(roomType.getPricePerNight() * 5)
        .firstName("firstName")
        .lastName("lastName")
        .email("firstName.lastName@email")
        .idCode("id_code")
        .build();

    var result = mapper.mapToDto(booking);

    assertEquals(booking.getId(), result.getId());
    assertEquals(booking.getStatus(), result.getStatus());
    assertEquals(booking.getStartDate().toLocalDate(), result.getStartDate());
    assertEquals(booking.getEndDate().toLocalDate(), result.getEndDate());
    assertEquals(booking.getPriceTotal(), result.getPriceTotal());

    var roomDetails = result.getRoomDetails();
    assertEquals(booking.getRoomType().getTitle(), roomDetails.getTitle());
    assertEquals(booking.getRoomType().getDescription(), roomDetails.getDescription());
    assertEquals(booking.getRoomType().getBedsCount(), roomDetails.getBedsCount());
    assertEquals(booking.getRoomType().getPreviewPictureUrl(), roomDetails.getPreviewPictureUrl());
    assertEquals(booking.getRoomType().getPricePerNight(), roomDetails.getPricePerNight());
    assertEquals(booking.getAssignedRoom().getRoomNumber(), roomDetails.getRoomNumber());

    var personData = result.getPersonData();
    assertEquals(booking.getFirstName(), personData.getFirstName());
    assertEquals(booking.getLastName(), personData.getLastName());
    assertEquals(booking.getEmail(), personData.getEmail());
    assertEquals(booking.getIdCode(), personData.getIdCode());
  }

  private RoomType getRoomType(long roomTypeId) {
    return
        RoomType.builder()
            .id(roomTypeId)
            .title("test")
            .description("description")
            .bedsCount(2)
            .pricePerNight(2.5)
            .previewPictureUrl("url")
            .build();
  }
}
