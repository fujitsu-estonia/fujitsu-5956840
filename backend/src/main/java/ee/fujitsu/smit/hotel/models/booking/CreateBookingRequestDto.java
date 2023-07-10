package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.models.DateRange;
import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.tools.validation.ValidDateRange;
import ee.fujitsu.smit.hotel.tools.validation.ValidIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**  DTO for create {@link ee.fujitsu.smit.hotel.entities.Booking} request */
@Setter
@Getter
public class CreateBookingRequestDto {

  @NotNull
  @Schema(type = "integer", format = "int64", description = "Selected room type id", example = "1")
  private long roomTypeId;

  @NotNull
  @ValidDateRange
  @Schema(
      type = "object",
      description = "Booking period start and end dates. Minimal duration is one day")
  private DateRange bookingPeriod;

  @NotNull
  @ValidIdentity(idCodeValidationRuleSets = ValidIdentity.IdCodeValidationRuleSet.ESTONIAN_NATIONAL_ID)
  @Schema(description = "Person data")
  private PersonData personData;
}
