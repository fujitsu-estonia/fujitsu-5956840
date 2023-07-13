package ee.fujitsu.smit.hotel.domain.event_handlers.impl;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import org.hibernate.event.spi.AbstractPreDatabaseOperationEvent;

/**
 * Database Operation {@link AbstractPreDatabaseOperationEvent pre-event} for {@link Booking}
 * entities only.
 *
 * @param <T> event type
 */
public abstract class AbstractBookingPreDatabaseOperationEventHandler<
    T extends AbstractPreDatabaseOperationEvent> {

  public final boolean handleEvent(T event) {
    if (!isBookingUpdateEvent(event)) {
      return false;
    }
    return handleEvent((Booking) event.getEntity(), event);
  }

  private boolean isBookingUpdateEvent(T event) {
    return event.getEntity() instanceof Booking;
  }

  protected abstract boolean handleEvent(Booking entity, T event);
}
