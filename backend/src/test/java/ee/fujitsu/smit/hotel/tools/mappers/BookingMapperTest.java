package ee.fujitsu.smit.hotel.tools.mappers;

import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.booking.BookingDatesConverter;
import ee.fujitsu.smit.hotel.tools.booking.BookingPriceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(BookingMapperTest.Config.class)
class BookingMapperTest {

  @Configuration
  static class Config {
    @Bean
    public BookingMapper bookingMapper() {
      return new BookingMapperImpl();
    }

  }

  @MockBean
  private RoomTypeRepository roomTypeRepository;

  @MockBean
  private BookingPriceCalculator bookingPriceCalculator;

  @MockBean
  private BookingDatesConverter bookingDatesConverter;

  @Autowired
  private BookingMapper mapper;

  @BeforeEach
  void setUp() {
  }

//  @Test
//  void calculatePriceTotal() {
//  }

  @Test
  void findRoomTypeById_whenRoomTypeNotFound_throwsNotFoundException() {
    var roomTypeId = 1L;

    when(roomTypeRepository.findById(roomTypeId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> mapper.findRoomTypeById(roomTypeId));
  }
}
