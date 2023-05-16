package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.request.TicketRequest;
import com.pim.projects.besttravel.api.model.responses.FlightResponse;
import com.pim.projects.besttravel.api.model.responses.TicketResponse;
import com.pim.projects.besttravel.domain.entity.Ticket;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.FlightRepository;
import com.pim.projects.besttravel.domain.repository.TicketRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITicketService;
import com.pim.projects.besttravel.infrastructure.helper.ApiCurrencyConnectorHelper;
import com.pim.projects.besttravel.infrastructure.helper.BlackListHelper;
import com.pim.projects.besttravel.infrastructure.helper.CustomerHelper;
import com.pim.projects.besttravel.util.BestTravelUtil;
import com.pim.projects.besttravel.util.enums.Tables;
import com.pim.projects.besttravel.util.exception.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor //inject dependencies automatically
public class TicketService implements ITicketService {

    private final FlightRepository flightRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final ApiCurrencyConnectorHelper apiCurrencyConnectorHelper;

    public static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

    @Override
    public TicketResponse create(TicketRequest request) {

        //validating is the customer is in black list
        blackListHelper.isBlackListCustomer(request.getIdCustomer());

        var flight = flightRepository.findById(request.getIdFlight()).orElseThrow(() -> new IdNotFoundException(Tables.flight.name()));
        var customer = customerRepository.findById(request.getIdCustomer()).orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));

        //create ticket to persist to db with the flight and customer request info
        var ticketToPersist = Ticket.builder()
                .id(UUID.randomUUID())
                .flight(flight)
                .customer(customer)
                .price(flight.getPrice().add(flight.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDate.now())
                .departureDate(BestTravelUtil.getRandomSoonDate())
                .arrivalDate(BestTravelUtil.getRandomLaterDate())
                .build();

        //persist ticket
        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

        //increase total flights
        this.customerHelper.increase(customer.getDni(), TicketService.class);

        log.info("Ticket saved with id: {}", ticketPersisted.getId());

        //return entity mapped to DTO response
        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));

        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
        var flight = flightRepository.findById(request.getIdFlight()).orElseThrow(() -> new IdNotFoundException(Tables.flight.name()));

        ticketToUpdate.setFlight(flight);
        ticketToUpdate.setPrice(flight.getPrice().add(flight.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomLaterDate());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLaterDate());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findFlightPrice(Long flightId, Currency currency) {
        var flight = this.flightRepository.findById(flightId).orElseThrow(() -> new IdNotFoundException(Tables.flight.name()));

        if (currency.equals(Currency.getInstance("EUR"))) return flight.getPrice().add(flight.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
        var currencyDTO = this.apiCurrencyConnectorHelper.getCurrency(currency);

        log.info("API currency in {}, response: {}", currencyDTO.getExchangeDate().toString(), currencyDTO.getRates());

        return flight.getPrice().add(flight.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)).multiply(currencyDTO.getRates().get(currency));
    }


    private TicketResponse entityToResponse(Ticket ticketEntity){
        var ticketResponse = new TicketResponse();

        //mapping entity to DTO
        BeanUtils.copyProperties(ticketEntity, ticketResponse);
        var flightResponse = new FlightResponse();
        BeanUtils.copyProperties(ticketEntity.getFlight(), flightResponse);

        ticketResponse.setFlight(flightResponse);

        return ticketResponse;
    }


}
