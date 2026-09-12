package com.TicketBooking.demo.common;

import java.util.UUID;

public class ShowNotFoundException extends NotFoundException {
    public ShowNotFoundException(UUID showId) {
        super("Show not found: " + showId);
    }
}
