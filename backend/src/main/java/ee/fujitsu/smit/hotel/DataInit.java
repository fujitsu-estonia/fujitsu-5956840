package ee.fujitsu.smit.hotel;

import ee.fujitsu.smit.hotel.entities.Booking;
import ee.fujitsu.smit.hotel.entities.Room;
import ee.fujitsu.smit.hotel.entities.RoomType;
import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.repositories.RoomRepository;
import ee.fujitsu.smit.hotel.repositories.RoomTypeRepository;
import ee.fujitsu.smit.hotel.tools.BookingTimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 */
@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final BookingTimeConverter bookingTimeConverter;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void run(String... args) {
        var rt1 = roomTypeRepository.save(RoomType.builder().title("Ühekohaline klassik tuba").description("Toas on 1 väike voodi").bedsCount(1).pricePerNight(98.00).build());
        var rt2 = roomTypeRepository.save(RoomType.builder().title("Kahekohaline klassik tuba").description("Toas on 1 suur 2 kohaline voodi").bedsCount(2).pricePerNight(148.00).build());
        var rt3 = roomTypeRepository.save(RoomType.builder().title("Kolmekohaline klassik tuba").description("Toas on 2 voodit, 1 suur 2 kohaline ja 1 väike 1 kohaline").bedsCount(3).pricePerNight(197.00).build());

        var r1 = roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1001").build());
        roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1002").build());
        roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1003").build());
        roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1004").build());
        roomRepository.save(Room.builder().roomType(rt1).roomNumber("K1005").build());
        roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2001").build());
        roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2002").build());
        roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2003").build());
        roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2004").build());
        roomRepository.save(Room.builder().roomType(rt2).roomNumber("K2005").build());
        roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3001").build());
        roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3002").build());
        roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3003").build());
        roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3004").build());
        roomRepository.save(Room.builder().roomType(rt3).roomNumber("K3005").build());

        LocalDate startDate = LocalDate.of(2023, 9, 7);
        LocalDate endDate = LocalDate.of(2023, 9, 9);
        bookingRepository.save(Booking.builder().startDate(bookingTimeConverter.mapBookingStartTime(startDate)).endDate(bookingTimeConverter.mapBookingEndTime(endDate)).status(BookingStatus.ACCEPTED).roomType(r1.getRoomType()).assignedRoom(r1).priceTotal(r1.getRoomType().getPricePerNight() * ChronoUnit.DAYS.between(startDate, endDate)).firstName("John").lastName("Smith").idCode("50001010017").email("John.Doe@gmail.com").build());
    }
}
