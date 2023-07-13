package ee.fujitsu.smit.hotel.domain.event_handlers.impl;

import ee.fujitsu.smit.hotel.domain.entities.EntityMeta;
import ee.fujitsu.smit.hotel.domain.entities.EntityMeta_;
import ee.fujitsu.smit.hotel.domain.event_handlers.PreInsertEventHandler;
import ee.fujitsu.smit.hotel.domain.event_handlers.PreUpdateEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.AbstractPreDatabaseOperationEvent;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreUpdateEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Interceptor for database pre-insert and pre-update events, that will add insert/update data to
 * entities of {@link EntityMeta} supertype.
 *
 * <p><a
 * href="http://anshuiitk.blogspot.com/2010/11/hibernate-pre-database-opertaion-event.html">reference</a>
 */
@Slf4j
@Component
public class EntityMetaHandler implements PreInsertEventHandler, PreUpdateEventHandler {

  public boolean handleEvent(PreInsertEvent event) {
    if (event.getEntity() instanceof EntityMeta entityMeta) {
      entityMeta.setCreatedAt(Instant.now());
      setStateValue(event, event.getState(), EntityMeta_.CREATED_AT, entityMeta.getCreatedAt());
    }
    return false;
  }

  public boolean handleEvent(PreUpdateEvent event) {
    if (event.getEntity() instanceof EntityMeta entityMeta) {
      entityMeta.setUpdatedAt(Instant.now());
      // update data
      setStateValue(event, event.getState(), EntityMeta_.UPDATED_AT, entityMeta.getUpdatedAt());
      // insert data
      setStateValue(event, event.getState(), EntityMeta_.CREATED_AT, entityMeta.getCreatedAt());
    }
    return false;
  }

  private void setStateValue(
      AbstractPreDatabaseOperationEvent event, Object[] state, String propertyName, Object value) {
    var index = getPropertyIndex(event, propertyName);
    state[index] = value;
  }

  private int getPropertyIndex(AbstractPreDatabaseOperationEvent event, String propertyName) {
    var propertyIndex = event.getPersister().getPropertyIndex(propertyName);
    if (propertyIndex < 0) {
      throw new IllegalStateException(
          String.format(
              "Entity '%s' must have property '%s'",
              event.getEntity().getClass().getName(), propertyName));
    }
    return propertyIndex;
  }
}
