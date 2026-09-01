package com.TicketBooking.demo.idempotency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "idempotency_key")
@Getter
@Setter
public class IdempotencyKey {
    @Id
    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String endpoint;

    @Column(name = "request_hash", nullable = false)
    private String requestHash;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "response_body")
    private String responseBody;

    @Column(name=  "created_at")
    private Instant createdAt;

    @Column(name=  "completed_at")
    private Instant completedAt;

    @PrePersist
    void onCreate(){
        this.createdAt = Instant.now();
    }
}
