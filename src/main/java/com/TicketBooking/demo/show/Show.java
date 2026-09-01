package com.TicketBooking.demo.show;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "show_event")
@Getter
@Setter
public class Show {

    @Id
    @Column(name="id")
    private UUID showId;
    @Column(name = "name", nullable = false)
    private String showName;
    @Column(name = "start_at", nullable = false)
    private Instant startTime;
    @Column(name = "created_at", nullable = false)
    private Instant createdTime;

    @PrePersist
    void onCreate(){
        this.createdTime = Instant.now();
    }
}
