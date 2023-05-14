package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.util.enums.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

public interface CatalogService <RS>{

    Page<RS> readAll(Integer page, Integer size, SortType sortType);

    Set<RS> readLowerPrice(BigDecimal price);

    Set<RS> readBetweenPrices(BigDecimal min, BigDecimal max);

    String FIELD_BY_SORT="price";

}
