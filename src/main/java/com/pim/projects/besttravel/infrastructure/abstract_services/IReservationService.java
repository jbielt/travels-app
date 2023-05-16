package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.request.ReservationRequest;
import com.pim.projects.besttravel.api.model.responses.ReservationResponse;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID> {

    BigDecimal findPrice(Long flightId, Currency currency);
}
