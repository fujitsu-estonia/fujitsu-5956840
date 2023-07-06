package ee.fujitsu.smit.hotel.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Database entity for storing the pictures of the rooms.
 *
 * <p>Reason for separate entity: possibility of reusing pictures for different {@link RoomType room
 * types}.
 *
 * <p>Alternative approach - store pictures on server. Rejected to keep trial task simple.
 */
@Entity
@Table(name = "room_picture")
@Getter
@Setter
public class RoomPicture implements Serializable {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob private byte[] data;
}
