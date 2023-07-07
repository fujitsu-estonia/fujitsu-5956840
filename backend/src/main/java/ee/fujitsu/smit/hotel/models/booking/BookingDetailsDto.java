package ee.fujitsu.smit.hotel.models.booking;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.models.PersonData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class BookingDetailsDto {

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      type = "string",
      format = "uuid",
      description = "Booking id",
      example = "2bde2f0c-f97e-4beb-92ab-7477dc7952f5")
  private UUID id;

  @Schema(
      type = "string",
      format = "date",
      description = "Booking starting date",
      example = "2023-07-05")
  private LocalDate startDate;

  @Schema(
      type = "string",
      format = "date",
      description = "Booking end date",
      example = "2023-07-07")
  private LocalDate endDate;

  @Schema(description = "Booking status", example = "REQUESTED")
  private BookingStatus status;

  @Schema(type = "object", description = "Booked room retails")
  private BookedRoomDetailsDto roomDetails;

  @Schema(type = "object", description = "Person data")
  private PersonData personData;
}
