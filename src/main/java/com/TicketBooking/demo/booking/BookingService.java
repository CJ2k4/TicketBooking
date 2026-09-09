package com.TicketBooking.demo.booking;

import com.TicketBooking.demo.booking.dto.HoldRequest;
import com.TicketBooking.demo.booking.dto.HoldResponse;
import com.TicketBooking.demo.seat.Seat;
import com.TicketBooking.demo.seat.SeatRepository;
import com.TicketBooking.demo.show.ShowNotFoundException;
import com.TicketBooking.demo.show.ShowRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingProperties bookingProperties;
    private final BookingRepository bookingRepository;

    BookingService(ShowRepository showRepository, SeatRepository seatRepository, BookingRepository bookingRepository,  BookingProperties bookingProperties) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
        this.bookingProperties = bookingProperties;
    }

    @Transactional
    public HoldResponse hold(UUID showId, HoldRequest holdRequest){
        if(!showRepository.existsById(showId)){
            throw new ShowNotFoundException(showId);
        }
        List<UUID> seatIds =  holdRequest.seatIds();

        //more than maxSeatsPerHold selected, so exception thrown
        if(seatIds.size() > bookingProperties.maxSeatsPerHold()){
            throw new InvalidSeatsException();
        }
        //duplicate seats
        if(seatIds.size() != new HashSet<>(seatIds).size()){
            throw new InvalidSeatsException();
        }

        List<Seat> seats = seatRepository.findByShowIdAndIdIn(showId, seatIds);

        //some are wrong seatIds, so repo doesn't return those seats so we get less amount of seat in seats List
        if(seats.size() != seatIds.size()){
            throw new InvalidSeatsException();
        }

        UUID holdId = UUID.randomUUID();
        UUID userId = holdRequest.userId();
        Instant expiresAt = Instant.now().plus(bookingProperties.holdDuration()) ;
        List<Booking> bookings = new ArrayList<>();
        for(UUID seatId : seatIds){
            Booking booking = new Booking();
            booking.setId(UUID.randomUUID());
            booking.setHoldId(holdId);
            booking.setSeatId(seatId);
            booking.setShowId(showId);
            booking.setUserId(userId);
            booking.setStatus(BookingStatus.HELD);
            booking.setHoldExpiresAt(expiresAt);
            bookings.add(booking);
        }
        try {
            bookingRepository.saveAllAndFlush(bookings);
        }catch(DataIntegrityViolationException e){
            throw new SeatUnavailableException();
        }
        return new HoldResponse(holdId, expiresAt, seatIds);
    }

}
