package ee.fujitsu.smit.hotel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;

/** Indicates a lifecycle stages of {@link Booking} entity */
public enum BookingStatus {
  /**
   * booking was accepted by the administrator
   *
   * <p>the {@link Room} can be assigned on this stage, if hotel is
   * willing to book certain rooms before the client has checked-in
   */
  ACCEPTED,
  /**
   * on booking start date, customer checked-in
   *
   * <p>{@link Room} should be assigned at this stage
   */
  STARTED,
  /** on booking end date (if not cancelled), customer checked-out */
  FINISHED,
  /** user cancels his/her booking */
  CANCELLED_BY_USER,
  /** administrator cancels booking request / started booking */
  CANCELLED_BY_ADMIN;

  /**
   * Checks if status belongs to cancelled statuses ({@link BookingStatus#CANCELLED_BY_USER} or
   * {@link BookingStatus#CANCELLED_BY_ADMIN})
   */
  public boolean isCancelled() {
    return this == CANCELLED_BY_USER || this == CANCELLED_BY_ADMIN;
  }

  @JsonCreator
  public static BookingStatus fromString(String str) {
    for (var val : values()) {
      if (val.toString().equalsIgnoreCase(str)) {
        return val;
      }
    }
    return null;
  }
}
