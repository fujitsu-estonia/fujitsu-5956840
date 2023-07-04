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

@Entity
@Getter
@Setter
@Table(name = "ROOMS")
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //TODO kas peavad integer olema
  @Column(name = "ROOM_NUMBER")
  private Integer roomNumber;

  //TODO kas peavad integer olema
  @Column(name = "BEDS")
  private Integer beds;

  @Column(name = "DESCRIPTION")
  private String description;
}
