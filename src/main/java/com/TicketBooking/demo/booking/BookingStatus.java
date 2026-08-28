package com.TicketBooking.demo.booking;

public enum BookingStatus {
    HELD,
    CONFIRMED,
    EXPIRED,
    CANCELLED;

    public boolean isActive(){
        return this == HELD || this == CONFIRMED;
    }
}
