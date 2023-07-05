package ee.fujitsu.smit.hotel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "ROOMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //TODO kas peavad integer olema
 // @Column(name = "ROOM_NUMBER")
  private Instant startDate;

  //TODO kas peavad integer olema
  //@Column(name = "BEDS")
  private Instant endDate;
}
