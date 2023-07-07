package ee.fujitsu.smit.hotel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "room_type")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType implements Serializable {

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, max = 512)
  private String title;

  @Lob
  private String description;

  @Column(name = "beds_count")
  private Integer bedsCount;

  @Column(name = "price_per_night")
  private Double pricePerNight;

  @Column(name = "preview_picture_url", length = 2048)
  private String previewPictureUrl;

}
