package ee.fujitsu.smit.hotel.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingAlreadyCancelledException;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingNotCancelledException;
import ee.fujitsu.smit.hotel.models.booking.DateRange;
import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.models.booking.BookedRoomDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.services.BookingService;
import ee.fujitsu.smit.hotel.tools.constants.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookingService bookingService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void bookRoom_withValidRequest_willReturnCreatedBookingId() throws Exception {
    var createdBookingId = UUID.randomUUID();
    when(bookingService.createBooking(any(CreateBookingRequestDto.class)))
        .thenReturn(createdBookingId);

    var createReq = new CreateBookingRequestDto();
    createReq.setRoomTypeId(1);
    createReq.setBookingPeriod(new DateRange(LocalDate.now(), LocalDate.now().plusDays(3)));
    createReq.setPersonData(personData("33706284279"));
    var content = objectMapper.writeValueAsString(createReq);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(quoted(createdBookingId)));
  }

  @Test
  void bookRoom_withInvalidRequest_willReturnValidationErrorMessagesList() throws Exception {
    var createdBookingId = UUID.randomUUID();
    when(bookingService.createBooking(any(CreateBookingRequestDto.class)))
        .thenReturn(createdBookingId);

    var createReq = new CreateBookingRequestDto();
    createReq.setRoomTypeId(1);
    createReq.setBookingPeriod(new DateRange(LocalDate.MAX, LocalDate.MIN));
    createReq.setPersonData(personData("invalidIdCode"));
    var content = objectMapper.writeValueAsString(createReq);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("errors").isArray())
        .andExpect(
            jsonPath("errors", String[].class)
                .value(
                    containsInAnyOrder(
                        Constants.ERROR_ID_CODE_INVALID,
                        Constants.ERROR_DATE_RANGE_ENDING_DATE_BEFORE,
                        Constants.ERROR_DATE_NOT_IN_FUTURE_NOR_PRESENT)));
  }

  @Test
  void cancelBooking_whenSuccessfullyCancelled_gives200OK() throws Exception {
    var bookingId = UUID.randomUUID();
    doReturn(true).when(bookingService).cancelBooking(eq(bookingId), anyBoolean());

    mockMvc
        .perform(MockMvcRequestBuilders.post("/api/booking/cancel/" + bookingId))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  void cancelBooking_whenBookingAlreadyCancelled_gives400BadRequest() throws Exception {
    var cancelledBookingId = UUID.randomUUID();
    doThrow(new BookingAlreadyCancelledException())
        .when(bookingService)
        .cancelBooking(eq(cancelledBookingId), anyBoolean());

    mockMvc
        .perform(MockMvcRequestBuilders.post("/api/booking/cancel/" + cancelledBookingId))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("errors", String[].class)
                .value(containsInAnyOrder(Constants.ERROR_BOOKING_ALREADY_CANCELLED)));
  }

  @Test
  void cancelBooking_whenBookingNotFound_gives400BadRequest() throws Exception {
    var invalidBookingId = UUID.randomUUID();
    doThrow(new NotFoundException())
        .when(bookingService)
        .cancelBooking(eq(invalidBookingId), anyBoolean());

    mockMvc
        .perform(MockMvcRequestBuilders.post("/api/booking/cancel/" + invalidBookingId))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("errors", String[].class).value(containsInAnyOrder(Constants.NOT_FOUND_CODE)));
  }

  @Test
  void cancelBooking_whenBookingCouldNotBeCancelled_gives500InternalServerError() throws Exception {
    var bookingId = UUID.randomUUID();
    doThrow(new BookingNotCancelledException())
        .when(bookingService)
        .cancelBooking(eq(bookingId), anyBoolean());

    mockMvc
        .perform(MockMvcRequestBuilders.post("/api/booking/cancel/" + bookingId))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("errors", String[].class)
                .value(containsInAnyOrder(Constants.ERROR_BOOKING_NOT_CANCELLED)));
  }

  @Test
  void getBookingDetails_whenBookingNotFound_gives400BadRequest() throws Exception {
    var invalidBookingId = UUID.randomUUID();
    doThrow(new NotFoundException()).when(bookingService).getBooking(invalidBookingId);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/booking/" + invalidBookingId))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("errors", String[].class).value(containsInAnyOrder(Constants.NOT_FOUND_CODE)));
  }

  @Test
  void getBookingDetails_whenBookingFound_returnsBookingDetails() throws Exception {
    var bookingId = UUID.randomUUID();
    var bookingDetails = bookingDetailsDto(bookingId);
    doReturn(bookingDetails).when(bookingService).getBooking(bookingId);

    var result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/booking/" + bookingId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    var body = result.getResponse().getContentAsString();
    var receivedDto = objectMapper.readValue(body, BookingDetailsDto.class);

    assertBookingDetailsEquals(bookingDetails, receivedDto);
  }

  @Test
  void searchBookings_willReturnResults() throws Exception {
    var bookingDetails = bookingDetailsDto(UUID.randomUUID());
    var bookingsSearch = new SearchBookingsDto();
    doReturn(List.of(bookingDetails)).when(bookingService).findBookings(bookingsSearch);

    var content = objectMapper.writeValueAsString(bookingsSearch);

    var result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/booking/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andReturn();

    var body = result.getResponse().getContentAsString();
    var receivedList = objectMapper.readValue(body, new TypeReference<List<BookingDetailsDto>>() {});

    assertNotNull(receivedList);
    assertEquals(1, receivedList.size());
    assertBookingDetailsEquals(bookingDetails, receivedList.get(0));
  }

  private static void assertBookingDetailsEquals(
      BookingDetailsDto expected, BookingDetailsDto actual) {
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getStatus(), actual.getStatus());
    assertEquals(expected.getStartDate(), actual.getStartDate());
    assertEquals(expected.getEndDate(), actual.getEndDate());
    assertEquals(expected.getPriceTotal(), actual.getPriceTotal());
    assertEquals(expected.getRoomDetails().getTitle(), actual.getRoomDetails().getTitle());
    assertEquals(
        expected.getRoomDetails().getDescription(), actual.getRoomDetails().getDescription());
    assertEquals(
        expected.getRoomDetails().getRoomNumber(), actual.getRoomDetails().getRoomNumber());
    assertEquals(expected.getRoomDetails().getBedsCount(), actual.getRoomDetails().getBedsCount());
    assertEquals(
        expected.getRoomDetails().getPricePerNight(), actual.getRoomDetails().getPricePerNight());
    assertEquals(
        expected.getRoomDetails().getPreviewPictureUrl(),
        actual.getRoomDetails().getPreviewPictureUrl());
    assertEquals(expected.getPersonData().getFirstName(), actual.getPersonData().getFirstName());
    assertEquals(expected.getPersonData().getLastName(), actual.getPersonData().getLastName());
    assertEquals(expected.getPersonData().getEmail(), actual.getPersonData().getEmail());
    assertEquals(expected.getPersonData().getIdCode(), actual.getPersonData().getIdCode());
  }

  private PersonData personData(String idCode) {
    return PersonData.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("firstName.lastName@email")
        .idCode(idCode)
        .build();
  }

  private BookingDetailsDto bookingDetailsDto(UUID bookingId) {
    var res = new BookingDetailsDto();
    res.setId(bookingId);
    res.setStatus(BookingStatus.ACCEPTED);
    res.setStartDate(LocalDate.of(2023, 1, 1));
    res.setEndDate(LocalDate.of(2023, 1, 10));
    res.setPriceTotal(12.5);
    res.setPersonData(
        PersonData.builder()
            .firstName("firstName")
            .lastName("lastName")
            .email("firstName.lastName@email")
            .idCode("id_code")
            .build());
    var roomDetails = new BookedRoomDetailsDto();
    roomDetails.setTitle("room1");
    roomDetails.setDescription("room description");
    roomDetails.setBedsCount(1);
    roomDetails.setRoomNumber("101");
    roomDetails.setPricePerNight(1.25);
    roomDetails.setPreviewPictureUrl("url");
    res.setRoomDetails(roomDetails);
    return res;
  }

  private String quoted(Object value) {
    return '"' + String.valueOf(value) + '"';
  }
}
