package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto;
import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto.CreateRoomGroup;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.SearchRoomDto;
import ee.fujitsu.smit.hotel.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/all")
  @Operation(description = "Get all rooms endpoint")
  public List<RoomDetailsDto> getAllRooms() {
    log.info("getAllRooms parameter");
    return roomService.getAllRooms();
  }

  //TODO do you really need request parameter
  // TODO kui palju tuleb siia otsingu parametreid?
  @GetMapping()
  @Operation(description = "Create order operation")
  public Page<RoomDetailsDto> findRoomByParameters(final SearchRoomDto searchProduct,
      final Pageable pageable) {
    log.info("findRoomByParameters {}", searchProduct);
    return roomService.findRoomByParameters(searchProduct, pageable);
  }

/*
  @GetMapping("{id}")
  public ResponseEntity<Room> getRoomById(@PathVariable("id") long id) {
    Optional<Room> roomData = roomService.findById(id);

    return roomData.map(room -> new ResponseEntity<>(room, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
*/

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Create room operation")
  public ResponseEntity<Long> createRoom(
      @Validated(CreateRoomGroup.class) @RequestBody CreateUpdateRoomRequestDto createRoomRequest) {
    log.info("createRoom with parameter {}", createRoomRequest);
    return new ResponseEntity<>(
        roomService.createRoom(createRoomRequest),
        HttpStatus.CREATED
    );
  }
}
