package com.TicketBooking.demo.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "idempotency_key")
public class IdempotencyKey {
    @Id
    private String key_value;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull
    private String endpoint;
}
