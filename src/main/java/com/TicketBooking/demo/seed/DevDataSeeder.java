package com.TicketBooking.demo.seed;

import com.TicketBooking.demo.seat.Seat;
import com.TicketBooking.demo.seat.SeatRepository;
import com.TicketBooking.demo.show.Show;
import com.TicketBooking.demo.show.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("dev")
@Slf4j
public class DevDataSeeder implements CommandLineRunner {
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    DevDataSeeder(SeatRepository seatRepository, ShowRepository showRepository) {
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
    }
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(showRepository.count() > 0) {
            log.info("Show seats already exist");
            return;
        }
        Show show = new Show();

        UUID id = UUID.randomUUID();
        show.setShowId(id);

        show.setShowName("My Show");

        show.setStartTime(Instant.now().plus(Duration.ofDays(7)));
        showRepository.save(show);
        List<Seat> seats = new ArrayList<>();
        for(int i =0; i<6 ; i++){
            String rowLabel = String.valueOf((char)('A'+i));
            for(int j = 0; j<10; j++) {
                Seat seat = new Seat();
                seat.setId(UUID.randomUUID());
                seat.setShowId(id);
                seat.setRowLabel(rowLabel);
                seat.setSeatNumber(j+1);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
        log.info("Seats saved");
    }
}
