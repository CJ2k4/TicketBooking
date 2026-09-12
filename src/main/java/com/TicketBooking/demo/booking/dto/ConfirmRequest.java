package com.TicketBooking.demo.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.UUID;

public record ConfirmRequest(@NotNull UUID userId){
}
