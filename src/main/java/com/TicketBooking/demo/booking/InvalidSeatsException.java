package com.TicketBooking.demo.booking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSeatsException extends RuntimeException {
    public InvalidSeatsException(){
        super("Invalid Seats");
    }
}
