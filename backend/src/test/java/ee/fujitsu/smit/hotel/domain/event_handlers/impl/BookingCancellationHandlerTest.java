package ee.fujitsu.smit.hotel.domain.event_handlers.impl;

import ee.fujitsu.smit.hotel.app_properties.BusinessLogicProperties;
import ee.fujitsu.smit.hotel.app_properties.dto.BookingsProperties;
import ee.fujitsu.smit.hotel.app_properties.dto.CancellationDeadline;
import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingCancellationDeadlineExceededException;
import org.hibernate.event.spi.PreUpdateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingCancellationHandlerTest {

  @Mock
  private BusinessLogicProperties appProperties;
  @Mock
  private BookingsProperties bookingsProperties;
  @Mock
  private CancellationDeadline cancellationDeadline;

  private BookingCancellationHandler handler;

  @BeforeEach
  void setUp() {
    doReturn(bookingsProperties).when(appProperties).booking();
    doReturn(cancellationDeadline).when(bookingsProperties).cancellationDeadline();

    handler = new BookingCancellationHandler(appProperties);
  }

  @Test
  void test_onlyBookingEntityIsHandled() {
    var handler = spy(this.handler);
    doReturn(true).when(handler).handleEvent(any(Booking.class), any(PreUpdateEvent.class));

    var event = mock(PreUpdateEvent.class);

    doReturn(new RoomType()).when(event).getEntity();

    handler.handleEvent(event);

    verify(handler, times(0)).handleEvent(any(), any());

    doReturn(new Booking()).when(event).getEntity();

    handler.handleEvent(event);

    verify(handler, times(1)).handleEvent(any(), any());
  }

  @Test
  void test_onlyBookingCancellationEventsAreChecked() {
    var event = mock(PreUpdateEvent.class);

    var booking = booking(BookingStatus.STARTED, LocalDateTime.now().plusDays(1));
    when(event.getEntity()).thenReturn(booking);

    assertDoesNotThrow(() -> handler.handleEvent(event));
  }

  @Test
  void test_whenBookingCancellationDeadlineNotExceeded_doesNotThrow() {
    when(cancellationDeadline.daysBeforeCheckIn()).thenReturn(3);

    var event = mock(PreUpdateEvent.class);
    when(event.getOldState()).thenReturn(new Object[] {BookingStatus.ACCEPTED});

    var booking = booking(BookingStatus.CANCELLED_BY_USER, LocalDateTime.now().plusDays(5));
    when(event.getEntity()).thenReturn(booking);

    assertDoesNotThrow(() -> handler.handleEvent(event));
  }

  @ParameterizedTest
  @ValueSource(ints = {3, 1, -2, -30})
  void test_whenBookingCancellationAttemptOverDeadline_throwsException(int plusDays) {
    when(cancellationDeadline.daysBeforeCheckIn()).thenReturn(3);

    var event = mock(PreUpdateEvent.class);
    when(event.getOldState()).thenReturn(new Object[] {BookingStatus.ACCEPTED});

    var booking = booking(BookingStatus.CANCELLED_BY_USER, LocalDateTime.now().plusDays(plusDays));
    when(event.getEntity()).thenReturn(booking);

    assertThrows(BookingCancellationDeadlineExceededException.class, () -> handler.handleEvent(event));

    booking = booking(BookingStatus.CANCELLED_BY_ADMIN, LocalDateTime.now().plusDays(plusDays));
    when(event.getEntity()).thenReturn(booking);

    assertThrows(BookingCancellationDeadlineExceededException.class, () -> handler.handleEvent(event));
  }

  private Booking booking(BookingStatus status, LocalDateTime start) {
    return Booking.builder()
        .id(UUID.randomUUID())
        .startDate(start)
        .endDate(start.plusDays(2))
        .status(status)
        .build();
  }
}
