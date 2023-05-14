package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.responses.FlyResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.IFlyService;
import com.pim.projects.besttravel.util.enums.SortType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {


    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        return null;
    }

    @Override
    public Set<FlyResponse> readLowerPrice(BigDecimal price) {
        return null;
    }

    @Override
    public Set<FlyResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return null;
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return null;
    }
}
