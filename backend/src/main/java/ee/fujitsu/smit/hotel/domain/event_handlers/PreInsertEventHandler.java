package ee.fujitsu.smit.hotel.domain.event_handlers;

import org.hibernate.event.spi.PreInsertEvent;

/** Handler for hibernate {@link PreInsertEvent pre-insert events} */
public interface PreInsertEventHandler {

  /**
   * Performs handler logic
   *
   * @param event pre-insert event
   * @return {@code true} if operation should be vetoed
   */
  boolean handleEvent(PreInsertEvent event);
}
