package ee.fujitsu.smit.hotel.domain.event_handlers.impl;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.event_handlers.PreUpdateEventHandler;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingCancellationDeadlineExceeded;
import ee.fujitsu.smit.hotel.tools.properties.BusinessLogicProperties;
import ee.fujitsu.smit.hotel.tools.properties.dto.CancellationDeadline;
import org.hibernate.event.spi.PreUpdateEvent;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * If the captured {@link Booking} update is booking cancellation, handler perform check, whether
 * the booking can be cancelled according to business rules ({@link CancellationDeadline}).
 */
@Component
public class BookingCancellationHandler
    extends AbstractBookingPreDatabaseOperationEventHandler<PreUpdateEvent>
    implements PreUpdateEventHandler {

  private final CancellationDeadline cancellationDeadline;

  public BookingCancellationHandler(BusinessLogicProperties appProperties) {
    cancellationDeadline = appProperties.booking().cancellationDeadline();
  }

  @Override
  public boolean handleEvent(Booking entity, PreUpdateEvent event) {
    if (isBookingCancellationUpdate(event, entity)) {
      canBeCancelledCheck(entity);
    }
    return false;
  }

  private boolean isBookingCancellationUpdate(PreUpdateEvent event, Booking entity) {
    var newStatus = entity.getStatus();
    if (newStatus == null || !newStatus.isCancelled()) {
      return false;
    }
    var oldStatus =
        Arrays.stream(event.getOldState())
            .filter(BookingStatus.class::isInstance)
            .map(BookingStatus.class::cast)
            .findFirst();
    return !oldStatus.map(BookingStatus::isCancelled).orElse(false);
  }

  private void canBeCancelledCheck(Booking entity) {
    var timeUntilCheckIn = Duration.between(LocalDateTime.now(), entity.getStartDate());

    if (timeUntilCheckIn.toDays() < cancellationDeadline.daysBeforeCheckIn()) {
      throw new BookingCancellationDeadlineExceeded();
    }
  }
}
