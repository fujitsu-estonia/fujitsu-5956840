package ee.fujitsu.smit.hotel.tools;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class BookingTimeConverter {
    public LocalDateTime mapBookingStartTime(LocalDate requestedStartTime) {
        if (requestedStartTime == null) {
            return null;
        }
        return requestedStartTime.atTime(18, 0, 0);
    }

    public LocalDateTime mapBookingEndTime(LocalDate requestedEndTime) {
        if (requestedEndTime == null) {
            return null;
        }
        return requestedEndTime.atTime(12, 0, 0);
    }
}
