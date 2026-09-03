package com.TicketBooking.demo.seat;

import com.TicketBooking.demo.booking.Booking;
import com.TicketBooking.demo.booking.BookingRepository;
import com.TicketBooking.demo.booking.BookingStatus;
import com.TicketBooking.demo.show.ShowNotFoundException;
import com.TicketBooking.demo.show.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    SeatService(SeatRepository seatRepository, ShowRepository showRepository, BookingRepository bookingRepository) {
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getAllSeats(UUID showId){
        if(!showRepository.existsById(showId)){
            throw new ShowNotFoundException(showId);
        }
        List<Seat> seats = seatRepository.findByShowIdOrderByRowLabelAscSeatNumberAsc(showId);
        Set<UUID> busySeatIds = bookingRepository.findByShowIdAndStatusIn(showId, BookingStatus.ACTIVE).stream().map(Booking::getSeatId).collect(Collectors.toSet());
        return mapToSeatResponse(seats, busySeatIds);
    }

    private List<SeatResponse> mapToSeatResponse(List<Seat> seats, Set<UUID> bookingIds){
        List<SeatResponse> seatResponses = new ArrayList<>();
        for(Seat seat : seats){
            UUID id = seat.getId();
            String  rowLabel = seat.getRowLabel();
            int seatNumber = seat.getSeatNumber();
            boolean available = !bookingIds.contains(id);
            SeatResponse seatResponse = new SeatResponse(id, rowLabel, seatNumber, available);
            seatResponses.add(seatResponse);
        }
        return seatResponses;
    }
}
