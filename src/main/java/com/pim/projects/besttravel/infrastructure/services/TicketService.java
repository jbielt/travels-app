package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.models.request.TicketRequest;
import com.pim.projects.besttravel.api.models.responses.TicketResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITicketService;

import java.util.UUID;

public class TicketService implements ITicketService {

    @Override
    public TicketResponse create(TicketRequest request) {
        return null;
    }

    @Override
    public TicketResponse read(UUID uuid) {
        return null;
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}
