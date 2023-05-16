package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.responses.FlightResponse;
import com.pim.projects.besttravel.domain.entity.Flight;
import com.pim.projects.besttravel.domain.repository.FlightRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.IFlightService;
import com.pim.projects.besttravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
//@AllArgsConstructor //lombock is not compatible with @Qualifier
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;
    private final WebClient webClient;


    public FlightService(FlightRepository flightRepository, @Qualifier(value = "currency") WebClient webClient) {
        this.flightRepository = flightRepository;
        this.webClient = webClient;
    }

    @Override
    public Page<FlightResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flightRepository.findAll(pageRequest).map(flight -> this.entityToResponse(flight)); //we can use method reference, but we use this for now to understand better
    }

    @Override
    public Set<FlightResponse> readByLowerPrice(BigDecimal price) {
        return flightRepository.findByPriceLessThan(price)
                .stream()
                .map(flight -> this.entityToResponse(flight))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return flightRepository.findByPriceBetween(min, max)
                .stream()
                .map(flight -> this.entityToResponse(flight))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightResponse> readByOriginDestiny(String origin, String destiny) {
        return flightRepository.findByOriginAndDestiny(origin, destiny)
                .stream()
                .map(flight -> this.entityToResponse(flight))
                .collect(Collectors.toSet());
    }

    private FlightResponse entityToResponse(Flight entity){
        FlightResponse response = new FlightResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
