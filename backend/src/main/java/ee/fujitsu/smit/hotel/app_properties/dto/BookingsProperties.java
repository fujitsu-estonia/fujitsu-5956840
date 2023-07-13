package ee.fujitsu.smit.hotel.app_properties.dto;

import ee.fujitsu.smit.hotel.app_properties.BusinessLogicProperties;

/**
 * @see BusinessLogicProperties
 */
public record BookingsProperties(
    CancellationDeadline cancellationDeadline, Time defaultCheckInTime, Time defaultCheckOutTime) {}
