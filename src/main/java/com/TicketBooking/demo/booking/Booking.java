package com.TicketBooking.demo.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {
    @Id
    private UUID id;

    @Column(name = "hold_id", nullable= false)
    private UUID holdId;

    @Column(name = "seat_id", nullable= false)
    private UUID seatId;

    @Column(name = "show_id", nullable= false)
    private UUID showId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BookingStatus status;

    @Column(name = "hold_expires_at", nullable = false)
    private Instant holdExpiresAt;

    @Column(name = "confirmed_at")
    private Instant confirmedAt;

    @Column(name = "booking_reference", length = 16)
    private String bookingReference;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable= false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable= false)
    private Instant updatedAt;

    public Booking() {
    }

    @PrePersist
    void onCreate(){
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate(){
        this.updatedAt = Instant.now();
    }
}
