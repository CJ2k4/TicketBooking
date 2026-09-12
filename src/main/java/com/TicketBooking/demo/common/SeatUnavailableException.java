package com.TicketBooking.demo.common;

public class SeatUnavailableException extends RuntimeException{
    public SeatUnavailableException() {
        super("Seat Unavailable");
    }
}
