package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.request.TourRequest;
import com.pim.projects.besttravel.api.model.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long> {

    void removeTicket(Long tourId, UUID ticketId);

    UUID addTicket(Long flightId, Long tourId);

    void removeReservation(Long tourId, UUID reservationId);

    UUID addReservation(Long reservationId, Long tourId, Integer totalDays);


}
