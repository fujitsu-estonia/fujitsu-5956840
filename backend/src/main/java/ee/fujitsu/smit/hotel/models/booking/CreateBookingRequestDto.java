package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.models.DateRange;
import ee.fujitsu.smit.hotel.models.PersonData;
import ee.fujitsu.smit.hotel.tools.validation.ValidDateRange;
import ee.fujitsu.smit.hotel.tools.validation.ValidIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/** Public api DTO for {@link ee.fujitsu.smit.hotel.entities.Booking} */
@Setter
@Getter
public class CreateBookingRequestDto {

  @NotNull
  @Schema(type = "integer", format = "int64", description = "Selected room type id", example = "1")
  private Long roomTypeId;

  @ValidDateRange
  @Schema(
      type = "object",
      description = "Booking period start and end dates. Minimal duration is one day")
  private DateRange bookingPeriod;

  @Valid
  @Schema(description = "Person data")
  @ValidIdentity(idCodeValidationRuleSets = ValidIdentity.IdCodeValidationRuleSet.ESTONIAN_NATIONAL_ID)
  private PersonData personData;
}
