package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.request.TicketRequest;
import com.pim.projects.besttravel.api.model.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    BigDecimal findFlightPrice(Long flightId);
}
