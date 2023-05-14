package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.request.TourRequest;

import java.io.Serializable;
import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourRequest, Long> {

    void removeTicket(UUID ticketId, Long tourId);

    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(UUID reservationId, Long tourId);

    UUID addReservation(Long reservationId, Long tourId);


}
