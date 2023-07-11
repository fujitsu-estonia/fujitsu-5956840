package ee.fujitsu.smit.hotel.domain.event_handlers;

import org.hibernate.event.spi.PreUpdateEvent;

/** Handler for hibernate {@link PreUpdateEvent pre-update events} */
public interface PreUpdateEventHandler {

  /**
   * Performs handler logic
   *
   * @param event pre-update event
   * @return {@code true} if operation should be vetoed
   */
  boolean handleEvent(PreUpdateEvent event);
}
