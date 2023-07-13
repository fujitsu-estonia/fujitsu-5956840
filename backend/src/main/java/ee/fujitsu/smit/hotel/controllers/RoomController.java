package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.models.room.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import ee.fujitsu.smit.hotel.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(path = "/api/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

  private final RoomService roomService;

  @PostMapping()
  @Operation(description = "Find rooms by parameters")
  public List<RoomDetailsDto> findRoomByParameters(final SearchRoomDto searchRoomDto) {
    return roomService.findRoomByParameters(searchRoomDto);
  }

  @GetMapping("{id}")
  @Operation(description = "Get room by id")
  public RoomDetailsDto getRoomByTypeId(@PathVariable("id") long id) {
    return roomService.getRoomDetails(id);
  }
}
