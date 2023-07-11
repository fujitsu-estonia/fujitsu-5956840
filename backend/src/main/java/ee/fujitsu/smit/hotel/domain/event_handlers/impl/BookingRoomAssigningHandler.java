package ee.fujitsu.smit.hotel.domain.event_handlers.impl;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.event_handlers.PreInsertEventHandler;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PreInsertEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingRoomAssigningHandler
    extends AbstractBookingPreDatabaseOperationEventHandler<PreInsertEvent>
    implements PreInsertEventHandler {

  private final RoomRepository roomRepository;

  @Override
  public boolean handleEvent(Booking entity, PreInsertEvent preInsertEvent) {
    // TODO: implement logic
    return false;
  }
}
