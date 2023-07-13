package ee.fujitsu.smit.hotel.repositories;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto.FilterParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static ee.fujitsu.smit.hotel.repositories.Conditions.id;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookingRepositoryTest extends DataManipulationTestBase {

  private Booking booking1;
  private Booking booking2;
  private Booking booking3;
  private Booking booking4;
  private Booking booking5;

  @BeforeAll
  void setUp() {

    booking1 = data.bookings().get(0);
    booking2 = data.bookings().get(1);
    booking3 = data.bookings().get(2);
    booking4 = data.bookings().get(3);
    booking5 = data.bookings().get(4);
  }

  @Test
  void findByStatusAndTimeBounds_withStatusesParam() {
    var searchParams = FilterParameters.builder().bookingStatus(BookingStatus.ACCEPTED).build();

    var found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).hasSize(1).first().has(id(booking1.getId()));

    searchParams =
        FilterParameters.builder()
            .bookingStatus(BookingStatus.STARTED)
            .bookingStatus(BookingStatus.CANCELLED_BY_USER)
            .build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found)
        .hasSize(2)
        .map(Booking::getId)
        .containsOnly(booking2.getId(), booking4.getId());
  }

  @Test
  void findByStatusAndTimeBounds_withFromDateParam() {
    var searchParams = FilterParameters.builder().fromDate(LocalDate.of(2023, 1, 1)).build();

    var found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).hasSize(5);

    searchParams = FilterParameters.builder().fromDate(LocalDate.of(2024, 1, 1)).build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).isEmpty();

    searchParams = FilterParameters.builder().fromDate(LocalDate.of(2023, 4, 16)).build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found)
        .hasSize(3)
        .map(Booking::getStartDate)
        .allMatch(
            startDate ->
                startDate.getMonthValue() > 4
                    || startDate.getMonthValue() == 4 && startDate.getDayOfMonth() >= 16);
  }

  @Test
  void findByStatusAndTimeBounds_withToDateParam() {
    var searchParams = FilterParameters.builder().toDate(LocalDate.of(2023, 1, 1)).build();

    var found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).isEmpty();

    searchParams = FilterParameters.builder().toDate(LocalDate.of(2024, 1, 1)).build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).hasSize(5);

    searchParams = FilterParameters.builder().toDate(LocalDate.of(2023, 4, 19)).build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found)
        .hasSize(2)
        .map(Booking::getEndDate)
        .allMatch(
            endDate ->
                endDate.getMonthValue() < 4
                    || endDate.getMonthValue() == 4 && endDate.getDayOfMonth() < 19);
  }

  @Test
  void findByStatusAndTimeBounds_withTimeBounds() {
    var parametersBuilder =
        FilterParameters.builder()
            .fromDate(LocalDate.of(2023, 4, 16))
            .toDate(LocalDate.of(2023, 4, 19));

    var searchParams = parametersBuilder.build();

    var found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).isEmpty();

    searchParams = parametersBuilder.toDate(LocalDate.of(2023, 4, 20)).build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found)
        .hasSize(2)
        .map(Booking::getId)
        .containsOnly(booking1.getId(), booking4.getId());
  }

  @Test
  void findByStatusAndTimeBounds_allParams() {
    var builder =
        FilterParameters.builder()
            .fromDate(LocalDate.of(2023, 3, 16))
            .toDate(LocalDate.of(2023, 4, 18))
            .bookingStatus(BookingStatus.ACCEPTED)
            .bookingStatus(BookingStatus.STARTED)
            .bookingStatus(BookingStatus.FINISHED);

    var searchParams = builder.build();

    var found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).hasSize(1).first().has(id(booking3.getId()));

    searchParams =
        builder
            .clearBookingStatuses()
            .bookingStatus(BookingStatus.CANCELLED_BY_USER)
            .bookingStatus(BookingStatus.CANCELLED_BY_ADMIN)
            .build();

    found = bookingRepository.findByStatusAndTimeBounds(toSearch(searchParams));

    assertThat(found).hasSize(1).first().has(id(booking5.getId()));
  }

  private SearchBookingsDto toSearch(FilterParameters parameters) {
    return new SearchBookingsDto(parameters, null);
  }

  private Booking.BookingBuilder booking(RoomType roomType, Room room) {
    return Booking.builder()
        .roomType(roomType)
        .assignedRoom(room)
        .firstName("firstName")
        .lastName("lastName")
        .email("firstName.lastName@test");
  }
}
