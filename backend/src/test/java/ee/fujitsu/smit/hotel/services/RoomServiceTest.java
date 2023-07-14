package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.repositories.models.RoomTypeExtended;
import ee.fujitsu.smit.hotel.services.impl.RoomServiceImpl;
import ee.fujitsu.smit.hotel.tools.mappers.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RoomServiceTest {
  @MockBean private RoomTypeRepository roomTypeRepository;
  @MockBean private RoomMapper roomMapper;

  private final long roomTypeId = 1;
  private RoomService roomService;

  @BeforeEach
  void setUp() {
    roomService = new RoomServiceImpl(roomMapper, roomTypeRepository);
  }

  @Test
  void getRoomDetails_whenRoomTypeNotFound_throwsNotFoundException() {
    doReturn(Optional.empty()).when(roomTypeRepository).findById(roomTypeId);

    assertThrows(NotFoundException.class, () -> roomService.getRoomDetails(roomTypeId));
  }

  @Test
  void findRoomByParameters() {
    var search =
        SearchRoomDto.builder()
            .beds(2)
            .startDate(LocalDate.of(2023, 4, 4))
            .endDate(LocalDate.of(2023, 5, 5))
            .build();

    var roomTypeExtended = mock(RoomTypeExtended.class);

    when(roomTypeRepository.getAvailableRoomTypesByBedsCountForPeriod(
            search.getBeds(), search.getStartDate(), search.getEndDate()))
        .thenReturn(List.of(roomTypeExtended));

    var found = roomService.findRoomByParameters(search);

    assertEquals(1, found.size());
  }
}
