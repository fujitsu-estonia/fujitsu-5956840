package ee.fujitsu.smit.hotel.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.room.RoomDetailsDto;
import ee.fujitsu.smit.hotel.models.room.SearchRoomDto;
import ee.fujitsu.smit.hotel.services.impl.RoomServiceImpl;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RoomServiceImpl roomService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void getRoomByTypeId_whenRoomNotFound_gives400BadRequest() throws Exception {
    var invalidRoomId = 2;
    doThrow(new NotFoundException()).when(roomService).getRoomDetails(invalidRoomId);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/rooms/" + invalidRoomId))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("errors", String[].class).value(containsInAnyOrder(Constants.NOT_FOUND_CODE)));
  }

  @Test
  void getRoomByTypeId_whenRoomFound_returnsRoomDetails() throws Exception {
    var roomTypeId = 1;
    var roomDetails = roomDetailsDto(roomTypeId);
    doReturn(roomDetails).when(roomService).getRoomDetails(roomTypeId);

    var result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/rooms/" + roomTypeId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    var body = result.getResponse().getContentAsString();
    var receivedDto = objectMapper.readValue(body, RoomDetailsDto.class);

    assertRoomDetailsEquals(roomDetails, receivedDto);
  }

  @Test
  void findRoomByParameters_willReturnResults() throws Exception {
    var roomDetails = roomDetailsDto(1);
    var roomsSearch = new SearchRoomDto();
    doReturn(List.of(roomDetails)).when(roomService).findRoomByParameters(roomsSearch);

    var content = objectMapper.writeValueAsString(roomsSearch);

    var result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/rooms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andReturn();

    var body = result.getResponse().getContentAsString();
    var receivedList = objectMapper.readValue(body, new TypeReference<List<RoomDetailsDto>>() {});

    assertNotNull(receivedList);
    assertEquals(1, receivedList.size());
    assertRoomDetailsEquals(roomDetails, receivedList.get(0));
  }

  private static void assertRoomDetailsEquals(RoomDetailsDto expected, RoomDetailsDto actual) {
    assertEquals(expected.getRoomTypeId(), actual.getRoomTypeId());
    assertEquals(expected.getTitle(), actual.getTitle());
    assertEquals(expected.getDescription(), actual.getDescription());
    assertEquals(expected.getBedsCount(), actual.getBedsCount());
    assertEquals(expected.getPricePerNight(), actual.getPricePerNight());
    assertEquals(expected.getPreviewPictureUrl(), actual.getPreviewPictureUrl());
    assertEquals(expected.getFreeRooms(), actual.getFreeRooms());
  }

  private RoomDetailsDto roomDetailsDto(long roomTypeId) {
    var res = new RoomDetailsDto();
    res.setRoomTypeId(roomTypeId);
    res.setTitle("Nice big room");
    res.setDescription("Nice big room with 2 small beds");
    res.setBedsCount(2);
    res.setPricePerNight(79.00);
    res.setPreviewPictureUrl("../../../assets/imgs/double.png");
    res.setFreeRooms(4);

    return res;
  }
}
