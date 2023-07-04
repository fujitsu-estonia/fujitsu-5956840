package ee.fujitsu.smit.hotel.model;

import java.util.Date;

public class Booking {
    private Customer user;
    private Room room;
    private Date from;
    private Date until;
    private Long orderNo;
    private String orderNoHash;
}
