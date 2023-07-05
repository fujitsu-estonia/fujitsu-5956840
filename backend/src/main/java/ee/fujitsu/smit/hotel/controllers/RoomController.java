package ee.fujitsu.smit.hotel.controllers;

import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto;
import ee.fujitsu.smit.hotel.models.CreateUpdateRoomRequestDto.CreateRoomGroup;
import ee.fujitsu.smit.hotel.models.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.SearchRoomDto;
import ee.fujitsu.smit.hotel.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(path = "/api/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

  private final RoomService roomService;

  @GetMapping()
  @Operation(description = "Find rooms by parameters")
  public List<RoomDetailsDto> findRoomByParameters(final SearchRoomDto searchRoomDto) {
    throw new NotImplementedException();
  }

  @GetMapping("{id}")
  @Operation(description = "Get room by id")
  public ResponseEntity<RoomDetailsDto> getRoomByTypeId(@PathVariable("id") long id) {
    throw new NotImplementedException();
  }

//  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
//  @Operation(description = "Create room operation")
//  public ResponseEntity<Long> createRoom(
//      @Validated(CreateRoomGroup.class) @RequestBody CreateUpdateRoomRequestDto createRoomRequest) {
//    log.info("createRoom with parameter {}", createRoomRequest);
//    return new ResponseEntity<>(
//        roomService.createRoom(createRoomRequest),
//        HttpStatus.CREATED
//    );
//  }
}
