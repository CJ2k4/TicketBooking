package com.TicketBooking.demo.booking;

import com.TicketBooking.demo.booking.dto.HoldRequest;
import com.TicketBooking.demo.booking.dto.HoldResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping("/shows/{showId}/holds")
    @ResponseStatus(HttpStatus.CREATED)
    public HoldResponse hold(@PathVariable UUID showId, @Valid @RequestBody HoldRequest holdRequest) {
        return bookingService.hold(showId, holdRequest);
    }
}
