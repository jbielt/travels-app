package com.pim.projects.besttravel.api.controller;

import com.pim.projects.besttravel.api.model.responses.FlyResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.IFlyService;
import com.pim.projects.besttravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

}
