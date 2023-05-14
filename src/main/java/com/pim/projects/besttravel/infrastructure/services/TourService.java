package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.request.TourRequest;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.FlyRepository;
import com.pim.projects.besttravel.domain.repository.HotelRepository;
import com.pim.projects.besttravel.domain.repository.TourRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITourService;
import com.pim.projects.besttravel.infrastructure.helper.TourHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;


    @Override
    public TourRequest create(TourRequest request) {
        return null;
    }

    @Override
    public TourRequest read(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long reservationId, Long tourId) {
        return null;
    }
}
