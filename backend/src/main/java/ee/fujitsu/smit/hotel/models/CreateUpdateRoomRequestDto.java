package ee.fujitsu.smit.hotel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_ID_NOT_NULL;
import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_ID_NULL;
import static ee.fujitsu.smit.hotel.tools.constants.Constants.ERROR_REQUIRED;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CreateUpdateRoomRequestDto {

  @Null(groups = {CreateRoomGroup.class}, message = ERROR_ID_NULL)
  @NotNull(groups = {UpdateRoomGroup.class}, message = ERROR_ID_NOT_NULL)
  private Long id;

  @NotNull(groups = {CreateRoomGroup.class, UpdateRoomGroup.class}, message = ERROR_REQUIRED)
  private Integer roomNumber;

  @NotNull(groups = {CreateRoomGroup.class, UpdateRoomGroup.class}, message = ERROR_REQUIRED)
  private Integer beds;

  @NotBlank(groups = {CreateRoomGroup.class, UpdateRoomGroup.class}, message = ERROR_REQUIRED)
  @Size(max = 255)
  private String description;

  public interface CreateRoomGroup {
    // marker for creation
  }

  public interface UpdateRoomGroup {
    // marker for creation
  }
}
