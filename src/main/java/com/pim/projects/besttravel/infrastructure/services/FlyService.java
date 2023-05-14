package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.responses.FlyResponse;
import com.pim.projects.besttravel.domain.entity.Fly;
import com.pim.projects.besttravel.domain.repository.FlyRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.IFlyService;
import com.pim.projects.besttravel.util.enums.SortType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;

    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(FlyResponse -> this.entityToResponse(FlyResponse)); //we can use method reference, but we use this for now to understand better
    }

    @Override
    public Set<FlyResponse> readByLowerPrice(BigDecimal price) {
        return flyRepository.findByPriceLessThan(price)
                .stream()
                .map(FlyResponse -> this.entityToResponse(FlyResponse))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return flyRepository.findByPriceBetween(min, max)
                .stream()
                .map(FlyResponse -> this.entityToResponse(FlyResponse))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return flyRepository.findByOriginAndDestiny(origin, destiny)
                .stream()
                .map(FlyResponse -> this.entityToResponse(FlyResponse))
                .collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(Fly entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
