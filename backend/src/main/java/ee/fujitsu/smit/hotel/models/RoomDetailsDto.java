package ee.fujitsu.smit.hotel.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDetailsDto {

  private Long id;
  private Integer roomNumber;
  private Integer beds;
  private String description;
}
