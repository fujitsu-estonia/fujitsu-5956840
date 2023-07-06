package ee.fujitsu.smit.hotel.constants;

/** Indicates a lifecycle stages of {@link ee.fujitsu.smit.hotel.entities.Booking} entity */
public enum BookingStatus {
  /** booking was requested by the user */
  REQUESTED,
  /**
   * booking was accepted by the administrator
   *
   * <p>the {@link ee.fujitsu.smit.hotel.entities.Room} can be assigned on this stage, if hotel is
   * willing to book certain rooms before the client has checked-in
   */
  ACCEPTED,
  /**
   * on booking start date, customer checked-in
   *
   * <p>{@link ee.fujitsu.smit.hotel.entities.Room} should be assigned at this stage
   */
  STARTED,
  /** on booking end date (if not cancelled), customer checked-out */
  FINISHED,
  /** user cancels his/her booking */
  CANCELLED_BY_USER,
  /** administrator cancels booking request / started booking */
  CANCELLED_BY_ADMIN
}
