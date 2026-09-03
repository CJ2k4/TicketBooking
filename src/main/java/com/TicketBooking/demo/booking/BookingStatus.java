package com.TicketBooking.demo.booking;

import java.util.*;

public enum BookingStatus {
    HELD,
    CONFIRMED,
    EXPIRED,
    CANCELLED;
    public static final List<BookingStatus> ACTIVE = List.of(BookingStatus.HELD, BookingStatus.CONFIRMED);

    public boolean isActive(){
        return ACTIVE.contains(this);
    }
}
