package ee.fujitsu.smit.hotel.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Dummy enum to identify who sent the request to the api. As this is the demo api, actual
 * user-management is left aside
 */
public enum RequestOriginator {
  USER,
  ADMIN;

  @JsonCreator
  public static RequestOriginator fromString(String str) {
    for (var val : values()) {
        if (val.toString().equalsIgnoreCase(str)) {
          return val;
        }
    }
    return null;
  }
}
