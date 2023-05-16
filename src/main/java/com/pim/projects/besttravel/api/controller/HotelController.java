package com.pim.projects.besttravel.api.controller;

import com.pim.projects.besttravel.api.model.responses.HotelResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.IHotelService;
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
@RequestMapping(path = "hotel")
@AllArgsConstructor
@Tag(name = "Hotel")
public class HotelController {

    private final IHotelService hotelService;

    @Operation(summary = "Return all the available Hotels paging and sorting")
    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType){
        if(Objects.isNull(sortType)){
            sortType = SortType.NONE;
        }
        var response = this.hotelService.readAll(page, size, sortType);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of Hotels with a lower price sent by parameter")
    @GetMapping(path = "lower-price")
    public ResponseEntity<Set<HotelResponse>> getLowerPrice(@RequestParam BigDecimal price){
        var response = this.hotelService.readByLowerPrice(price);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of Hotels between prices sent by paremeter")
    @GetMapping(path = "between-price")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrices(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        var response = this.hotelService.readBetweenPrices(min, max);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of Hotels with a rating greater than the one sent by parameter")
    @GetMapping(path = "rating")
    public ResponseEntity<Set<HotelResponse>> getByRatingGreaterThan(@RequestParam Integer rating){
        if(rating > 4) rating = 4;
        if(rating < 1) rating = 1;

        var response = this.hotelService.readByRatingGreaterThan(rating);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


}
