package ee.fujitsu.smit.hotel.domain.listener;

import ee.fujitsu.smit.hotel.domain.event_handlers.PreUpdateEventHandler;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Listener for {@link PreUpdateEvent Pre-Update Events}, which will trigger given {@link
 * PreUpdateEventHandler Pre-Insert Event Handlers} for performing intercepting logic before saving
 * object updates to the database.
 *
 * <p>Inspired by <a href="https://github.com/psinghal04/hibernate-events-example">this</a>
 */
@Component
@RequiredArgsConstructor
public class UpdateListener implements PreUpdateEventListener {

  private final List<PreUpdateEventHandler> handlers;

  @Override
  public boolean onPreUpdate(PreUpdateEvent event) {
    for (var handler : handlers) {
      if (handler.handleEvent(event)) {
        return true;
      }
    }
    return false;
  }
}
