package com.pim.projects.besttravel.api.controller;

import com.pim.projects.besttravel.api.model.responses.FlyResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.IFlyService;
import com.pim.projects.besttravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "fly")
@AllArgsConstructor
public class FlyController {

    private final IFlyService flyService;


    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType){
        if(Objects.isNull(sortType)){
            sortType = SortType.NONE;
        }
        var response = this.flyService.readAll(page, size, sortType);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "lower-price")
    public ResponseEntity<Set<FlyResponse>> getLowerPrice(@RequestParam BigDecimal price){
        var response = this.flyService.readByLowerPrice(price);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between-price")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrices(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        var response = this.flyService.readBetweenPrices(min, max);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "origin-destiny")
    public ResponseEntity<Set<FlyResponse>> getOriginDestiny(@RequestParam String origin, @RequestParam String destiny){
        var response = this.flyService.readByOriginDestiny(origin, destiny);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


}
