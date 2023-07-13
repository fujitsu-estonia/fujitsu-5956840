package ee.fujitsu.smit.hotel.services.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.exceptions.room.NoAvailableRoomsException;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Service for automatic assigning of rooms to the new bookings.
 *
 * <p>Service is responsible for providing the available room of correct type for the booking, but
 * also responsible for ensuring that the room will be reserved for the booking for required period.
 * The booking creation is a transactional operation thus on database level room becomes 'reserved'
 * only after the transaction is committed. In order to keep transactions isolated, in addition to
 * DB query filtering, rooms are ensured to be actually available with the application-level 'locks'
 * for selected rooms for the booking periods, which will preserve until the new booking is
 * persisted, and will be released afterward ({@link
 * BookingRoomAssigner#addTemporalLocalRoomPeriodicLock(Room, RoomReservationPeriod) lock method}).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingRoomAssigner {

  private final RoomRepository roomRepository;

  private final Map<Long, Lock> roomTypeLocks = new ConcurrentHashMap<>();
  private final Cache<Long, List<RoomReservationPeriod>> roomsPeriodicLocksWithExpiration =
      CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).build();

  public void assignAvailableRoom(Booking entity) {
    if (entity.getAssignedRoom() == null) {
      tryAssignRoomForBookingRequest(entity);
    }
  }

  private void tryAssignRoomForBookingRequest(Booking entity) {
    var roomType = entity.getRoomType();

    if (roomType == null) {
      throw new IllegalStateException("Booking can not be saved without having room type");
    }

    var roomTypeLock = getRoomTypeLock(roomType);

    try {
      roomTypeLock.lock();

      var reservationPeriod = new RoomReservationPeriod(entity.getStartDate(), entity.getEndDate());

      var room = findAvailableRoom(roomType, reservationPeriod);

      addTemporalLocalRoomPeriodicLock(room, reservationPeriod);

      entity.setAssignedRoom(room);

    } finally {
      roomTypeLock.unlock();
    }
  }

  private Lock getRoomTypeLock(RoomType roomType) {
    return roomTypeLocks.computeIfAbsent(roomType.getId(), k -> new ReentrantLock());
  }

  /**
   * Handles the situation when one booking got assigned room, but was not yet saved to DB, and
   * another room of the same room type required for next booking. Given lock will make the first
   * room unavailable for selecting for second booking.
   */
  @SneakyThrows
  private void addTemporalLocalRoomPeriodicLock(
      Room room, RoomReservationPeriod reservationPeriod) {
    roomsPeriodicLocksWithExpiration.get(room.getId(), ArrayList::new).add(reservationPeriod);
  }

  private @NonNull Room findAvailableRoom(
      RoomType roomType, RoomReservationPeriod reservationPeriod) {
    return roomRepository
        .findAvailableRoomsOfTypeByPeriod(
            roomType, reservationPeriod.from(), reservationPeriod.to())
        .stream()
        .filter(room -> roomIsNotReservedForPeriodWithLocalLock(room, reservationPeriod))
        .findFirst()
        .orElseThrow(() -> new NoAvailableRoomsException(roomType.getTitle()));
  }

  private boolean roomIsNotReservedForPeriodWithLocalLock(
      Room room, RoomReservationPeriod reservationPeriod) {
    return Optional.ofNullable(roomsPeriodicLocksWithExpiration.getIfPresent(room.getId())).stream()
        .flatMap(Collection::stream)
        .allMatch(periodNotOverlapWith(reservationPeriod));
  }

  private Predicate<RoomReservationPeriod> periodNotOverlapWith(
      RoomReservationPeriod reservationPeriod) {
    return alreadyReservedPeriod ->
        alreadyReservedPeriod.from().isAfter(reservationPeriod.to())
            || alreadyReservedPeriod.to().isBefore(reservationPeriod.from());
  }

  private record RoomReservationPeriod(LocalDateTime from, LocalDateTime to) {}
}
