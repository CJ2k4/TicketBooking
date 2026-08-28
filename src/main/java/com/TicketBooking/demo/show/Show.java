package com.TicketBooking.demo.show;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "show_event")
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

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getStartTime() {
        return startTime;
    }
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    public UUID getShowId() {
        return showId;
    }
    public void setShowId(UUID showId) {
        this.showId = showId;
    }
    public String getShowName() {
        return showName;
    }
    public void setShowName(String showName) {
        this.showName = showName;
    }
}
