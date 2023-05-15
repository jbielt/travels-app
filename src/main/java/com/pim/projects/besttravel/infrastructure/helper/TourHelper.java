package com.pim.projects.besttravel.infrastructure.helper;

import com.pim.projects.besttravel.domain.entity.*;
import com.pim.projects.besttravel.domain.repository.ReservationRepository;
import com.pim.projects.besttravel.domain.repository.TicketRepository;
import com.pim.projects.besttravel.infrastructure.services.ReservationService;
import com.pim.projects.besttravel.infrastructure.services.TicketService;
import com.pim.projects.besttravel.util.BestTravelUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;


    public Set<Ticket> createTickets(Set<Fly> flights, Customer customer){
        var response = new HashSet<Ticket>(flights.size());
        flights.forEach(fly -> {
            var ticketToPersist = Ticket.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGES_PRICE_PERCENTAGE)))
                    .purchaseDate(LocalDate.now())
                    .departureDate(BestTravelUtil.getRandomSoonDate())
                    .arrivalDate(BestTravelUtil.getRandomLaterDate())
                    .build();

            response.add(this.ticketRepository.save(ticketToPersist));
        });

        return response;
    }

    public Set<Reservation> createReservations(HashMap<Hotel, Integer> hotels, Customer customer){
        var response = new HashSet<Reservation>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            var reservationToPersist = Reservation.builder()
                    .id(UUID.randomUUID())
                    .hotel(hotel)
                    .customer(customer)
                    .totalDays(totalDays)
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)))
                    .build();

            response.add(this.reservationRepository.save(reservationToPersist));
        });

        return response;
    }

    public Ticket createTicket(Fly fly, Customer customer){
        var ticketToPersist = Ticket.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGES_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDate.now())
                .departureDate(BestTravelUtil.getRandomSoonDate())
                .arrivalDate(BestTravelUtil.getRandomLaterDate())
                .build();
        return this.ticketRepository.save(ticketToPersist);
    }

    public Reservation createReservation(Hotel hotel, Customer customer, Integer totalDays){
        var reservationToPersist = Reservation.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(totalDays)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)))
                .build();

        return this.reservationRepository.save(reservationToPersist);
    }
}
