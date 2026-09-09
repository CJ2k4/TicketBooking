package com.TicketBooking.demo.booking;

import java.util.*;

public enum BookingStatus {
    HELD,
    CONFIRMED,
    EXPIRED,
    CANCELLED;
    public static final Set<BookingStatus> ACTIVE = Set.of(BookingStatus.HELD, BookingStatus.CONFIRMED);

    public boolean isActive(){
        return ACTIVE.contains(this);
    }
}
