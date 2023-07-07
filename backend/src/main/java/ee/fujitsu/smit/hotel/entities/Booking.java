package ee.fujitsu.smit.hotel.entities;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Enumerated(EnumType.ORDINAL)
  private BookingStatus status;

  @ManyToOne
  @JoinColumn(name = "room_type_id", nullable = false)
  private RoomType roomType;

  @ManyToOne
  @JoinColumn(name = "assigned_room_id")
  private Room assignedRoom;

  @Column(name = "price_total")
  private Double priceTotal;

  // user data
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "id_code", length = 32)
  private String idCode;

  @Email
  private String email;

}
