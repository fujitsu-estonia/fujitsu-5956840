package ee.fujitsu.smit.hotel.models;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorsDto {

  @ArraySchema(
      arraySchema = @Schema(description = "errors list"),
      schema = @Schema(description = "error code", example = "error_code.example"))
  private List<String> errors;
}
