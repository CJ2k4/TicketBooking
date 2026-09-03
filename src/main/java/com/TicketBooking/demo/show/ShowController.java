package com.TicketBooking.demo.show;

import com.TicketBooking.demo.seat.SeatResponse;
import com.TicketBooking.demo.seat.SeatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private SeatService seatService;

    public ShowController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{showId}/seats")
    public List<SeatResponse> getAllSeat(@PathVariable UUID showId) {
        return seatService.getAllSeats(showId);
    }
}
