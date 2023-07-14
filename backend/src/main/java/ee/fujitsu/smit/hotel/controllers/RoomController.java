package ee.fujitsu.smit.hotel.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import ee.fujitsu.smit.hotel.models.ErrorsDto;
import ee.fujitsu.smit.hotel.models.room.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import ee.fujitsu.smit.hotel.services.impl.RoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "/api/rooms")
public class RoomController {

  private final RoomServiceImpl roomService;

  @PostMapping()
  @Operation(
      description = "Find rooms types by parameters",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "search parameters",
              required = true,
              content =
                  @Content(
                      mediaType = APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = SearchRoomDto.class))),
      responses =
          @ApiResponse(
              description = "Room search results",
              responseCode = "200",
              content =
                  @Content(
                      mediaType = APPLICATION_JSON_VALUE,
                      array =
                          @ArraySchema(
                              arraySchema = @Schema(description = "found room types list"),
                              schema = @Schema(implementation = RoomDetailsDto.class)))))
  public List<RoomDetailsDto> findRoomByParameters(@RequestBody SearchRoomDto searchRoomDto) {
    return roomService.findRoomByParameters(searchRoomDto);
  }

  @GetMapping("{id}")
  @Operation(
      description = "Get room by id",
      parameters =
          @Parameter(
              name = "id",
              in = ParameterIn.PATH,
              description = "Id of room type to be found",
              required = true),
      responses = {
        @ApiResponse(
            description = "Room type successfully found",
            responseCode = "200",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(
                            description = "Found room type data",
                            implementation = RoomDetailsDto.class))),
        @ApiResponse(
            description = "Room type not found",
            responseCode = "400",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorsDto.class)))
      })
  public RoomDetailsDto getRoomByTypeId(@PathVariable("id") long id) {
    return roomService.getRoomDetails(id);
  }
}
