package ee.fujitsu.smit.hotel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "room_type")
public class RoomType implements Serializable {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @Column(name = "beds_count")
  private Integer bedsCount;

  @Column(name = "price_per_night")
  private Double pricePerNight;

  @ManyToOne
  @JoinColumn(name = "preview_picture_id")
  private RoomPicture previewPicture;

}
