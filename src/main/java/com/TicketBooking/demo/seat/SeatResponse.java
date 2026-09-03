package com.TicketBooking.demo.seat;

import java.util.UUID;

public record SeatResponse(UUID id, String rowLabel, int seatNumber, boolean available) {
}
