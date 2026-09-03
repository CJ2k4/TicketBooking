package com.TicketBooking.demo.show;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShowNotFoundException extends RuntimeException{
    public ShowNotFoundException(UUID showId) {
        super("Show not found: " + showId);
    }
}
