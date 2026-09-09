package com.TicketBooking.demo.booking.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record HoldResponse(UUID holdId, Instant expiresAt, List<UUID> seatIds) {
}
