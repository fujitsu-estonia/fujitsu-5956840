package ee.fujitsu.smit.hotel.tools.properties.dto;

/**
 * @see ee.fujitsu.smit.hotel.tools.properties.BusinessLogicProperties
 */
public record BookingsProperties(
    CancellationDeadline cancellationDeadline, Time defaultCheckInTime, Time defaultCheckOutTime) {}
