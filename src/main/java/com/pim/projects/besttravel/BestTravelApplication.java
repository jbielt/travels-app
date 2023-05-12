package com.pim.projects.besttravel;

import com.pim.projects.besttravel.domain.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {


    private final HotelRepository hotelRepository;
    private final FlyRepository flyRepository;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BestTravelApplication(
            HotelRepository hotelRepository,
            FlyRepository flyRepository,
            TicketRepository ticketRepository,
            ReservationRepository reservationRepository,
            TourRepository tourRepository,
            CustomerRepository customerRepository
    ) {
        this.hotelRepository = hotelRepository;
        this.flyRepository = flyRepository;
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
        this.tourRepository = tourRepository;
        this.customerRepository = customerRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        var fly = flyRepository.findById(15L).get(); //we use .get() because if not returns Optional
//        var hotel = hotelRepository.findById(7L).get();
//        var ticket = ticketRepository.findById(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
//        var reservation = reservationRepository.findById(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();
//        var customer = customerRepository.findById("BBMB771012HMCRR022").get();

//        log.info(String.valueOf(fly));
//        log.info(String.valueOf(hotel));
//        log.info(String.valueOf(ticket));
//        log.info(String.valueOf(reservation));
//        log.info(String.valueOf(customer));

        this.flyRepository.findByPriceLessThan(BigDecimal.valueOf(20)).forEach(fly -> System.out.println(fly));
        this.flyRepository.findByPriceBetween(BigDecimal.valueOf(10), BigDecimal.valueOf(15)).forEach(fly -> System.out.println(fly));
        this.flyRepository.findByOriginAndDestiny("Grecia", "Mexico").forEach(fly -> System.out.println(fly));
    }
}
