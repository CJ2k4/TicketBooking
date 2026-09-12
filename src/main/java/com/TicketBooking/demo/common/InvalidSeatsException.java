package com.TicketBooking.demo.common;

public class InvalidSeatsException extends RuntimeException {
    public InvalidSeatsException(){
        super("Invalid Seats");
    }
}
