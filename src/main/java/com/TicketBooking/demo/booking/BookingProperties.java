package com.TicketBooking.demo.booking;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "booking")
public record BookingProperties(Duration holdDuration, int maxSeatsPerHold) {
}
