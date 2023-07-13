package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.domain.entities.Booking_;
import ee.fujitsu.smit.hotel.domain.entities.EntityMeta_;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SearchBookingsDto {

  public static final FilterParameters EMPTY_FILTER = new FilterParameters();

  public static final List<OrderParameter> DEFAULT_ORDER_BY =
      List.of(new OrderParameter(EntityMeta_.CREATED_AT, Sort.Direction.DESC));

  @Nullable
  @Schema(
      description = "Parameters for filtering booking results",
      implementation = FilterParameters.class)
  private FilterParameters filterBy;

  @Nullable
  @ArraySchema(
      arraySchema = @Schema(description = "Parameters for ordering booking results"),
      schema = @Schema(implementation = OrderParameter.class))
  @Valid
  private List<OrderParameter> orderBy;

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FilterParameters {
    @Nullable
    @Singular
    @ArraySchema(
        arraySchema =
            @Schema(description = "Search bookings with given statuses only", nullable = true),
        schema = @Schema(implementation = BookingStatus.class))
    private List<BookingStatus> bookingStatuses;

    @Nullable
    @Schema(
        type = "string",
        format = "date",
        description = "Search bookings from date (inclusive)",
        nullable = true)
    private LocalDate fromDate;

    @Nullable
    @Schema(
        type = "string",
        format = "date",
        description = "Search bookings up to date (exclusive)",
        nullable = true)
    private LocalDate toDate;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderParameter {

    @Schema(
        description = "Order parameter name",
        allowableValues = {
          Booking_.STATUS,
          Booking_.ROOM_TYPE,
          Booking_.PRICE_TOTAL,
          Booking_.FIRST_NAME,
          Booking_.LAST_NAME,
          Booking_.ID_CODE,
          EntityMeta_.CREATED_AT,
          EntityMeta_.UPDATED_AT,
        })
    @Pattern(
        regexp =
            Booking_.STATUS
                + '|'
                + Booking_.ROOM_TYPE
                + '|'
                + Booking_.PRICE_TOTAL
                + '|'
                + Booking_.FIRST_NAME
                + '|'
                + Booking_.LAST_NAME
                + '|'
                + Booking_.ID_CODE
                + '|'
                + EntityMeta_.CREATED_AT
                + '|'
                + EntityMeta_.UPDATED_AT,
        message = "invalid order parameter name")
    private String name;

    @Schema(
        description = "Order parameter direction",
        implementation = Sort.Direction.class,
        defaultValue = "ASC")
    private Sort.Direction direction = Sort.Direction.ASC;
  }
}
