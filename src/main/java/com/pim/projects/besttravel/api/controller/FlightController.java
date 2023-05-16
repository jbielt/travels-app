package com.pim.projects.besttravel.api.controller;

import com.pim.projects.besttravel.api.model.responses.FlightResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.IFlightService;
import com.pim.projects.besttravel.util.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "flight")
@AllArgsConstructor
@Tag(name = "Flight")
public class FlightController {

    private final IFlightService flightService;


    @Operation(summary = "Return all the available Flights paging and sorting")
    @GetMapping
    public ResponseEntity<Page<FlightResponse>> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType){
        if(Objects.isNull(sortType)){
            sortType = SortType.NONE;
        }
        var response = this.flightService.readAll(page, size, sortType);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of Flights with a lower price sent by parameter")
    @GetMapping(path = "lower-price")
    public ResponseEntity<Set<FlightResponse>> getLowerPrice(@RequestParam BigDecimal price){
        var response = this.flightService.readByLowerPrice(price);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of Flights between prices sent by paremeter")
    @GetMapping(path = "between-price")
    public ResponseEntity<Set<FlightResponse>> getBetweenPrices(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        var response = this.flightService.readBetweenPrices(min, max);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of Flights with an Origin and Destiny sent by parameter")
    @GetMapping(path = "origin-destiny")
    public ResponseEntity<Set<FlightResponse>> getOriginDestiny(@RequestParam String origin, @RequestParam String destiny){
        var response = this.flightService.readByOriginDestiny(origin, destiny);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


}