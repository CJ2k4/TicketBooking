package com.TicketBooking.demo.booking.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ConfirmResponse (String bookingReference, Instant confirmedAt, List<UUID> seatIds){
}
