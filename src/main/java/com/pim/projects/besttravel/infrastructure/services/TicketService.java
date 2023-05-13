package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.request.TicketRequest;
import com.pim.projects.besttravel.api.model.responses.FlyResponse;
import com.pim.projects.besttravel.api.model.responses.TicketResponse;
import com.pim.projects.besttravel.domain.entity.Ticket;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.FlyRepository;
import com.pim.projects.besttravel.domain.repository.TicketRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITicketService;
import com.pim.projects.besttravel.util.BestTravelUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor //inject dependencies automatically
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    private static final BigDecimal CHARGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

    @Override
    public TicketResponse create(TicketRequest request) {
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        //create ticket to persist to db with the fly and customer request info
        var ticketToPersist = Ticket.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDate.now())
                .departureDate(BestTravelUtil.getRandomSoonDate())
                .arrivalDate(BestTravelUtil.getRandomLaterDate())
                .build();

        //persist ticket
        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

        log.info("Ticket saved with id: {}", ticketPersisted.getId());

        //return entity mapped to DTO response
        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow();

        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow();
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomLaterDate());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLaterDate());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updatedwith id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findFlyPrice(Long flyId) {
        var fly = this.flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE));
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
