package ee.fujitsu.smit.hotel.services.impl;

import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.room.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.services.RoomService;
import ee.fujitsu.smit.hotel.tools.mappers.RoomMapper;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

  private final RoomMapper mapper;
  private final RoomTypeRepository roomTypeRepository;

  @Override
  public @NonNull List<RoomDetailsDto> findRoomByParameters(@NonNull SearchRoomDto searchRoomDto) {
    return roomTypeRepository
        .getAvailableRoomTypesByBedsCountForPeriod(
            searchRoomDto.getBeds(), searchRoomDto.getStartDate(), searchRoomDto.getEndDate())
        .stream()
        .map(mapper::mapToDto)
        .toList();
  }

  @Override
  public RoomDetailsDto getRoomDetails(long roomTypeId) {
    return roomTypeRepository
        .findById(roomTypeId)
        .map(mapper::mapToDto)
        .orElseThrow(NotFoundException::new);
  }
}
