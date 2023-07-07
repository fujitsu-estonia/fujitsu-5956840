package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.SearchRoomDto;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.mappers.RoomMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoomService {

  private final RoomMapper mapper;
  private final RoomTypeRepository roomTypeRepository;

  public List<RoomDetailsDto> findRoomByParameters(final SearchRoomDto searchRoomDto) {
    // TODO: implement repofilter
    return roomTypeRepository.findAll().stream().map(mapper::mapToDto).toList();
  }

  public RoomDetailsDto getRoomDetails(Long roomTypeId) {
    return roomTypeRepository.findById(roomTypeId).map(mapper::mapToDto).orElseThrow(NotFoundException::new);
  }

}
