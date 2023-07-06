package ee.fujitsu.smit.hotel.tools;

public class Constants {

  private Constants() {
  }

  public static final String GLOBAL_ERRORS_KEY = "errors";

  // error codes
  public static final String NOT_FOUND_CODE = "error.notFound";
  public static final String ERROR_REQUIRED = "error.required";
  public static final String ERROR_ID_NULL = "error.id.null";
  public static final String ERROR_ID_NOT_NULL = "error.id.notNull";
  public static final String ERROR_ID_CODE_INVALID = "error.invalid_IdCode";

  public static final String ERROR_DATE_RANGE_INVALID = "error.invalid_dateRange";
  public static final String ERROR_DATE_RANGE_ENDING_DATE_BEFORE = ERROR_DATE_RANGE_INVALID + ".ending_date_first";
  public static final String ERROR_DATE_RANGE_TOO_SHORT = ERROR_DATE_RANGE_INVALID + ".too_small_range";

}

