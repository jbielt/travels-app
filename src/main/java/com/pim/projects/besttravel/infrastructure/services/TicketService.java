package com.pim.projects.besttravel.infrastructure.services;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.pim.projects.besttravel.api.models.request.TicketRequest;
import com.pim.projects.besttravel.api.models.responses.FlyResponse;
import com.pim.projects.besttravel.api.models.responses.TicketResponse;
import com.pim.projects.besttravel.domain.entity.Ticket;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.FlyRepository;
import com.pim.projects.besttravel.domain.repository.TicketRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITicketService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor //inject dependencies automatically
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

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

    private TicketResponse entityToResponse(Ticket ticketEntity){
        var ticketResponse = new TicketResponse();

        //mapping entity to DTO
        BeanUtils.copyProperties(ticketEntity, ticketResponse);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(ticketEntity.getFly(), flyResponse);

        ticketResponse.setFly(flyResponse);

        return ticketResponse;
    }
}
