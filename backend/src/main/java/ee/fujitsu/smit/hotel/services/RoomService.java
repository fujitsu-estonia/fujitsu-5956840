package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.entities.Room;
import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.SearchRoomDto;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import ee.fujitsu.smit.hotel.tools.mappers.RoomMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoomService {

  private final RoomMapper mapper;
  private final RoomRepository roomRepository;

  public List<RoomDetailsDto> getAllRooms() {
    return roomRepository.findAll()
        .stream()
        .map(mapper::mapToDto)
        .toList();
  }

  public Page<RoomDetailsDto> findRoomByParameters(final SearchRoomDto searchRoomDto,
      final Pageable pageable) {
    // TODO peame arutleme mis loogika siia peab minna ja kui palju parameteid filtreeritakse
    // var room = roomRepository.findById(id).orElseThrow(NotFoundException::new);
    //return mapper.map(room, RoomDetailsDto.class);
    return new PageImpl<>(List.of(new RoomDetailsDto()));
  }

  @Transactional
  public Long createRoom(final CreateUpdateRoomRequestDto request) {
    Room passedRoom = mapper.mapToEntity(request);
    Room createdNewRoom = roomRepository.saveAndFlush(passedRoom);
    return createdNewRoom.getId();
  }
}
