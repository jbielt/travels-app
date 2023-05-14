package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.request.TourRequest;
import com.pim.projects.besttravel.api.model.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long> {

    void removeTicket(UUID ticketId, Long tourId);

    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(UUID reservationId, Long tourId);

    UUID addReservation(Long reservationId, Long tourId);


}
