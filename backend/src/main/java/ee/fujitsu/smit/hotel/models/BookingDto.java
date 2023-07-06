package ee.fujitsu.smit.hotel.models;

import ee.fujitsu.smit.hotel.tools.validation.ValidDateRange;
import ee.fujitsu.smit.hotel.tools.validation.ValidIdCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/** Public api DTO for {@link ee.fujitsu.smit.hotel.entities.Booking} */
@Setter
@Getter
public class BookingDto {

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      type = "string",
      format = "uuid",
      description = "Booking id",
      example = "2bde2f0c-f97e-4beb-92ab-7477dc7952f5")
  private UUID id;

  @Schema(type = "integer", format = "int64", description = "Selected room type id", example = "1")
  private Long roomTypeId;

  @Schema(
      type = "object",
      description = "Booking period start and end dates. Minimal duration is one day")
  @ValidDateRange(minRange = @ValidDateRange.TemporalValue(value = 1, unit = ChronoUnit.DAYS))
  private DateRange bookingPeriod;

  @Schema(type = "string", description = "Person first name", example = "John")
  private String firstName;

  @Schema(type = "string", description = "Person last name", example = "Doe")
  private String lastName;

  @Schema(
      type = "string",
      format = "id-code",
      description = "Person national identification number",
      example = "50001010017")
  @ValidIdCode(allowedRuleSets = ValidIdCode.ValidationRuleSet.ESTONIAN_NATIONAL_ID)
  private String idCode;

  @Schema(type = "string", description = "Person e-mail address", example = "John.Doe@gmail.com")
  @Email
  private String email;
}
