package ee.fujitsu.smit.hotel.app_properties.dto;

import ee.fujitsu.smit.hotel.app_properties.BusinessLogicProperties;

/**
 * Configured deadline rules:
 *
 * <ul>
 *   <li>daysBeforeCheckIn - value (days) before the booking check-in time, after exceeding which
 *       the booking can not be cancelled anymore
 * </ul>
 *
 * @see BusinessLogicProperties
 */
public record CancellationDeadline(int daysBeforeCheckIn) {}
