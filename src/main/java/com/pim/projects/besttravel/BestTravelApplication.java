package com.pim.projects.besttravel;

import com.pim.projects.besttravel.domain.entity.Reservation;
import com.pim.projects.besttravel.domain.entity.Ticket;
import com.pim.projects.besttravel.domain.entity.Tour;
import com.pim.projects.besttravel.domain.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
        log.info("Customer name: " + customer.getFullName());

        var fly = flyRepository.findById(11L).orElseThrow();
        log.info("Fly name " + fly.getOriginName() + "-" + fly.getDestinyName());

        var hotel = hotelRepository.findById(3L).orElseThrow();
        log.info("Hotel name: " + hotel.getName());

        var tour = Tour.builder()
                .customer(customer)
                .build();

        var ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .price(fly.getPrice().multiply(BigDecimal.TEN))
                .arrivalDate(LocalDate.now())
                .departureDate(LocalDate.now())
                .purchaseDate(LocalDate.now())
                .customer(customer)
                .tour(tour)
                .fly(fly)
                .build();

        var reservation = Reservation.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateEnd(LocalDate.now().plusDays(2))
                .dateStart(LocalDate.now().plusDays(1))
                .hotel(hotel)
                .customer(customer)
                .tour(tour)
                .totalDays(1)
                .price(hotel.getPrice().multiply(BigDecimal.TEN))
                .build();

        System.out.println("----SAVING TOUR-----");

        tour.addReservation(reservation);
        tour.updateReservations();
        tour.addTicket(ticket);
        tour.updateTickets();

        var tourSaved = this.tourRepository.save(tour);
        Thread.sleep(8000);
        this.tourRepository.deleteById(tourSaved.getId());

    }
}
