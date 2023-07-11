package ee.fujitsu.smit.hotel.domain.listener;

import ee.fujitsu.smit.hotel.domain.event_handlers.PreInsertEventHandler;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Listener for {@link PreInsertEvent Pre-Insert Events}, which will trigger given {@link
 * PreInsertEventHandler Pre-Insert Event Handlers} for performing intercepting logic before saving
 * new object to the database.
 *
 * <p>Inspired by <a href="https://github.com/psinghal04/hibernate-events-example">this</a>
 */
@Component
@RequiredArgsConstructor
public class InsertListener implements PreInsertEventListener {

  private final List<PreInsertEventHandler> handlers;

  @Override
  public boolean onPreInsert(PreInsertEvent event) {
    for (var handler : handlers) {
      if (handler.handleEvent(event)) {
        return true;
      }
    }
    return false;
  }
}
