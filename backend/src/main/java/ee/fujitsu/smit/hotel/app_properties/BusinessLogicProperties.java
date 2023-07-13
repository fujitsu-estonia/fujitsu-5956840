package ee.fujitsu.smit.hotel.app_properties;

import ee.fujitsu.smit.hotel.app_properties.dto.BookingsProperties;

/**
 * Application business logic related properties defined in separate file. File should be of Yaml
 * format. File location can be given as Maven argument {@code -Dapp.businessLogic.properties}.
 * Default properties file location: {@code classpath:hotel-properties.yaml}
 *
 * @see ee.fujitsu.smit.hotel.configs.BusinessLogicPropertiesConfig
 */
public record BusinessLogicProperties(BookingsProperties booking) {}
