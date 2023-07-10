package ee.fujitsu.smit.hotel;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.entities.Room;
import ee.fujitsu.smit.hotel.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.BookingDatesConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/** */
@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

  private final BookingDatesConverter bookingDatesConverter;
  private final RoomTypeRepository roomTypeRepository;
  private final RoomRepository roomRepository;
  private final BookingRepository bookingRepository;

  @Override
  public void run(String... args) {
    var rt1 =
        roomTypeRepository.save(
            RoomType.builder()
                .title("Ühekohaline klassik tuba")
                .description("Toas on 1 väike voodi")
                .bedsCount(1)
                .pricePerNight(98.00)
                .previewPictureUrl("../../../assets/imgs/single.png")
                .build());
    var rt2 =
        roomTypeRepository.save(
            RoomType.builder()
                .title("Kahekohaline klassik tuba")
                .description("Toas on 1 suur 2 kohaline voodi")
                .bedsCount(2)
                .pricePerNight(148.00)
                .previewPictureUrl("../../../assets/imgs/double.png")
                .build());
    var rt3 =
        roomTypeRepository.save(
            RoomType.builder()
                .title("Kolmekohaline klassik tuba")
                .description("Toas on 2 voodit, 1 suur 2 kohaline ja 1 väike 1 kohaline")
                .bedsCount(3)
                .pricePerNight(197.00)
                .previewPictureUrl("../../../assets/imgs/triple.png")
                .build());

    var r11 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1001").build());
    var r12 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1002").build());
    var r13 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1003").build());
    var r14 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1004").build());
    var r15 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1005").build());
    var r21 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2001").build());
    var r22 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2002").build());
    var r23 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2003").build());
    var r24 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2004").build());
    var r25 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2005").build());
    var r31 = roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3001").build());
    var r32 = roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3002").build());
    var r33 = roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3003").build());
    var r34 = roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3004").build());
    var r35 = roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3005").build());

    createBooking(r11, LocalDate.of(2023, 9, 7), LocalDate.of(2023, 9, 9));
    createBooking(r12, LocalDate.of(2023, 9, 8), LocalDate.of(2023, 9, 9));
    createBooking(r13, LocalDate.of(2023, 9, 7), LocalDate.of(2023, 9, 8));

    createBooking(r21, LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 10));
    createBooking(r22, LocalDate.of(2023, 9, 6), LocalDate.of(2023, 9, 9));
    createBooking(r23, LocalDate.of(2023, 9, 7), LocalDate.of(2023, 9, 8));
    createBooking(r24, LocalDate.of(2023, 9, 6), LocalDate.of(2023, 9, 8));
    createBooking(r25, LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 6));

    createBooking(r31, LocalDate.of(2023, 10, 5), LocalDate.of(2023, 10, 6));
  }

  private void createBooking(Room room, LocalDate startDate, LocalDate endDate) {
    bookingRepository.save(
        Booking.builder()
            .startDate(bookingDatesConverter.mapBookingStartTime(startDate))
            .endDate(bookingDatesConverter.mapBookingEndTime(endDate))
            .status(BookingStatus.ACCEPTED)
            .roomType(room.getRoomType())
            .assignedRoom(room)
            .priceTotal(
                room.getRoomType().getPricePerNight() * ChronoUnit.DAYS.between(startDate, endDate))
            .firstName("John")
            .lastName("Smith")
            .idCode("50001010017")
            .email("John.Doe@gmail.com")
            .build());
  }
}
