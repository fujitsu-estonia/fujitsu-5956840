package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class BookingDto {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY, type = "string", format = "uuid", description = "Booking id", example = "2bde2f0c-f97e-4beb-92ab-7477dc7952f5")
  private UUID id;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY, type = "integer", format = "int64", description = "Booking number", example = "123")
  private Long bookingNumber;

  @Schema(type = "integer", format = "int64", description = "Room type id", example = "1")
  private Long roomTypeId;

  @Schema(type = "string", format = "date-time", description = "Booking start date", example = "2023-07-05T12:00:00Z")
  private LocalDateTime startDate;

  @Schema(type = "string", format = "date-time", description = "Booking end date", example = "2023-07-07T12:00:00Z")
  private LocalDateTime endDate;

  @Schema(type = "string", description = "Person first name", example = "John")
  private String firstName;

  @Schema(type = "string", description = "Person last name",  example = "Doe")
  private String lastName;

  @Schema(type = "string", description = "Person national identification number",  example = "50001010017")
  private String personId;

  @Schema(type = "string", description = "Person e-mail address",  example = "John.Doe@gmail.com")
  private String email;

}
