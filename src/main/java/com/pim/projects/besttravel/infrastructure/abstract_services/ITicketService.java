package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.models.request.TicketRequest;
import com.pim.projects.besttravel.api.models.responses.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{
}
