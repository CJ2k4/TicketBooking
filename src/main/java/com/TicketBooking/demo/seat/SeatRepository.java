package com.TicketBooking.demo.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {

    List<Seat> findByShowIdOrderByRowLabelAscSeatNumberAsc(UUID showId);

    List<Seat> findByShowIdAndIdIn(UUID showId, List<UUID> seatIds);
}
