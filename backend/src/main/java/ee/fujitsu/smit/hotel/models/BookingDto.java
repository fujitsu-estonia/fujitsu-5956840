package ee.fujitsu.smit.hotel.models;

import ee.fujitsu.smit.hotel.entities.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BookingDto {
    private Customer user;
    private Room room;
    private Date from;
    private Date until;
    private Long orderNo;
    private String orderNoHash;
}
