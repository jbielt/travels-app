package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.request.TourRequest;
import com.pim.projects.besttravel.api.model.responses.TourResponse;
import com.pim.projects.besttravel.domain.entity.Fly;
import com.pim.projects.besttravel.domain.entity.Hotel;
import com.pim.projects.besttravel.domain.entity.Tour;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.FlyRepository;
import com.pim.projects.besttravel.domain.repository.HotelRepository;
import com.pim.projects.besttravel.domain.repository.TourRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITourService;
import com.pim.projects.besttravel.infrastructure.helper.CustomerHelper;
import com.pim.projects.besttravel.infrastructure.helper.TourHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;


    @Override
    public TourResponse create(TourRequest request) {
        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();

        var flights = new HashSet<Fly>();
        request.getFlights().forEach(fly -> {
            flights.add(
                    this.flyRepository.findById(fly.getId()).orElseThrow()
            );
        });

        var hotels = new HashMap<Hotel, Integer>();
        request.getHotels().forEach(hotel -> {
            hotels.put(
                    this.hotelRepository.findById(hotel.getId()).orElseThrow(),
                    hotel.getTotalDays()
            );
        });

        var tourToSave = Tour.builder()
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();

        var tourSaved = this.tourRepository.save(tourToSave);

        //increase total tours
        this.customerHelper.increase(customer.getDni(), TourService.class);

        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream().map(Reservation -> Reservation.getId()).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(Ticket -> Ticket.getId()).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDB = this.tourRepository.findById(id).orElseThrow();
        return TourResponse.builder()
                .reservationIds(tourFromDB.getReservations().stream().map(Reservation -> Reservation.getId()).collect(Collectors.toSet()))
                .ticketIds(tourFromDB.getTickets().stream().map(Ticket -> Ticket.getId()).collect(Collectors.toSet()))
                .id(tourFromDB.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow();
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket( Long tourId, UUID ticketId) {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourToUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourToUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var fly = this.flyRepository.findById(flyId).orElseThrow();
        var ticket = tourHelper.createTicket(fly, tourToUpdate.getCustomer());
        tourToUpdate.addTicket(ticket);
        this.tourRepository.save(tourToUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourToUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourToUpdate);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        var reservation = tourHelper.createReservation(hotel, tourToUpdate.getCustomer(), totalDays);
        tourToUpdate.addReservation(reservation);
        this.tourRepository.save(tourToUpdate);
        return reservation.getId();
    }
}
