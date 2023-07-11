package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonData {

  @Schema(type = "string", description = "Person first name", example = "John")
  private String firstName;

  @Schema(type = "string", description = "Person last name", example = "Doe")
  private String lastName;


  @Schema(
      type = "string",
      format = "id-code",
      description = "Person national identification number",
      example = "50001010017",
      nullable = true)
  private String idCode;

  @Schema(
      accessMode = Schema.AccessMode.WRITE_ONLY,
      type = "boolean",
      description = "Flag to mark if the id code can be left undefined (for example for foreigners)"
  )
  @Builder.Default
  private boolean ignoreIdCode = false;

  @Email
  @Schema(type = "string", description = "Person e-mail address", example = "John.Doe@gmail.com")
  private String email;
}
