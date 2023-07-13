package ee.fujitsu.smit.hotel.domain.listener;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

/**
 * Sets up Hibernate Database Events listeners, which will perform additional checks/operations when
 * event is triggered. Note that from within Event Handlers the usage of {@link
 * jakarta.persistence.EntityManager} and repositories may be causing various problems (i.e.
 * StackOverflowException), thus should be done with utmost care.
 *
 * <p>Inspired by <a href="https://github.com/psinghal04/hibernate-events-example">this</a>
 */
@Component
@RequiredArgsConstructor
public class HibernateListenerConfigurer {

  @PersistenceUnit private EntityManagerFactory entityManagerFactory;

  private final UpdateListener updateListener;
  private final InsertListener insertListener;

  @PostConstruct
  protected void init() {
    SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);

    EventListenerRegistry registry =
        sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

    registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(updateListener);
    registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(insertListener);
  }
}
