package com.TicketBooking.demo.seat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    private UUID id;

    @Column(name = "show_id", nullable = false)
    private UUID showId;

    @Column(name = "row_label", nullable = false)
    private String rowLabel;

    @Column(name="seat_number", nullable = false)
    private int seatNumber;
}
