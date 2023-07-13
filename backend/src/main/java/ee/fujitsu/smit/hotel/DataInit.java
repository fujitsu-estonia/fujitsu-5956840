package ee.fujitsu.smit.hotel;

import ee.fujitsu.smit.hotel.domain.entities.Booking;
import ee.fujitsu.smit.hotel.domain.entities.Room;
import ee.fujitsu.smit.hotel.domain.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.booking.BookingDatesConverter;
import ee.fujitsu.smit.hotel.tools.booking.BookingPriceCalculator;
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
  private final BookingPriceCalculator bookingPriceCalculator;
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
    var rt11 =
        roomTypeRepository.save(
            RoomType.builder()
                .title("Ühekohaline Deluxe tuba")
                .description("Toas on 1 väike voodi, merevaade")
                .bedsCount(1)
                .pricePerNight(109.00)
                .previewPictureUrl("../../../assets/imgs/deluxe-single.png")
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
    var rt22 =
        roomTypeRepository.save(
            RoomType.builder()
                .title("Kahekohaline Deluxe tuba")
                .description("Toas on 1 suur 2 kohaline voodi, merevaade")
                .bedsCount(2)
                .pricePerNight(162.00)
                .previewPictureUrl("../../../assets/imgs/deluxe-double.png")
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
    var rt33 =
        roomTypeRepository.save(
            RoomType.builder()
                .title("Kolmekohaline Deluxe tuba")
                .description("Toas on 2 voodit, 1 suur 2 kohaline ja 1 väike 1 kohaline, merevaade")
                .bedsCount(3)
                .pricePerNight(211.00)
                .previewPictureUrl("../../../assets/imgs/deluxe-triple.png")
                .build());

    var r11 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1001").build());
    var r12 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1002").build());
    var r13 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1003").build());
    var r14 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1004").build());
    roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1005").build());
    var r16 = roomRepository.save(Room.builder().roomType(rt11).roomNumber("K1006").build());
    var r17 = roomRepository.save(Room.builder().roomType(rt11).roomNumber("K1007").build());
    roomRepository.save(Room.builder().roomType(rt11).roomNumber("K1008").build());
    roomRepository.save(Room.builder().roomType(rt11).roomNumber("K1009").build());
    roomRepository.save(Room.builder().roomType(rt11).roomNumber("K1010").build());
    var r21 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2001").build());
    var r22 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2002").build());
    var r23 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2003").build());
    var r24 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2004").build());
    var r25 = roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2005").build());
    roomRepository.save(Room.builder().roomType(rt22).roomNumber("K2006").build());
    roomRepository.save(Room.builder().roomType(rt22).roomNumber("K2007").build());
    roomRepository.save(Room.builder().roomType(rt22).roomNumber("K2008").build());
    roomRepository.save(Room.builder().roomType(rt22).roomNumber("K2009").build());
    roomRepository.save(Room.builder().roomType(rt22).roomNumber("K2010").build());
    var r31 = roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3001").build());
    roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3002").build());
    roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3003").build());
    roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3004").build());
    roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3005").build());
    roomRepository.save(Room.builder().roomType(rt33).roomNumber("K3006").build());
    roomRepository.save(Room.builder().roomType(rt33).roomNumber("K3007").build());
    roomRepository.save(Room.builder().roomType(rt33).roomNumber("K3008").build());
    roomRepository.save(Room.builder().roomType(rt33).roomNumber("K3009").build());
    roomRepository.save(Room.builder().roomType(rt33).roomNumber("K3010").build());

    createBooking(r11, LocalDate.of(2023, 9, 7), LocalDate.of(2023, 9, 9));
    createBooking(r12, LocalDate.of(2023, 9, 8), LocalDate.of(2023, 9, 9));
    createBooking(r13, LocalDate.of(2023, 9, 7), LocalDate.of(2023, 9, 8));

    createBooking(r21, LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 10));
    createBooking(r22, LocalDate.of(2023, 9, 6), LocalDate.of(2023, 9, 9));
    createBooking(r23, LocalDate.of(2023, 9, 7), LocalDate.of(2023, 9, 8));
    createBooking(r24, LocalDate.of(2023, 9, 6), LocalDate.of(2023, 9, 8));
    createBooking(r25, LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 6));

    createBooking(r31, LocalDate.of(2023, 10, 5), LocalDate.of(2023, 10, 6));

    createBooking(r14, LocalDate.of(2023, 7, 13), LocalDate.of(2023, 7, 19));

    createBooking(r16, LocalDate.of(2023, 8, 13), LocalDate.of(2023, 8, 15));
    createBooking(r17, LocalDate.of(2023, 8, 13), LocalDate.of(2023, 8, 19));
    createBooking(r16, LocalDate.of(2023, 8, 17), LocalDate.of(2023, 8, 19));
  }

  private void createBooking(Room room, LocalDate startDate, LocalDate endDate) {
    var bookingDuration = ChronoUnit.DAYS.between(startDate, endDate);
    bookingRepository.save(
        Booking.builder()
            .startDate(bookingDatesConverter.mapBookingStartTime(startDate))
            .endDate(bookingDatesConverter.mapBookingEndTime(endDate))
            .status(BookingStatus.ACCEPTED)
            .roomType(room.getRoomType())
            .assignedRoom(room)
            .priceTotal(
                bookingPriceCalculator.calculateBookingPrice(room.getRoomType(), bookingDuration))
            .firstName("John")
            .lastName("Smith")
            .idCode("50001010017")
            .email("John.Doe@gmail.com")
            .build());
  }
}
