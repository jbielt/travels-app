package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.responses.HotelResponse;
import com.pim.projects.besttravel.domain.entity.Hotel;
import com.pim.projects.besttravel.domain.repository.HotelRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.IHotelService;
import com.pim.projects.besttravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.hotelRepository.findAll(pageRequest).map(HotelResponse -> this.entityToResponse(HotelResponse));
    }

    @Override
    public Set<HotelResponse> readByLowerPrice(BigDecimal price) {
        return hotelRepository.findByPriceLessThan(price)
                .stream()
                .map(HotelResponse -> this.entityToResponse(HotelResponse))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return hotelRepository.findByPriceIsBetween(min, max)
                .stream()
                .map(HotelResponse -> this.entityToResponse(HotelResponse))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readByRatingGreaterThan(Integer rating) {
        return hotelRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(HotelResponse -> this.entityToResponse(HotelResponse))
                .collect(Collectors.toSet());
    }



    private HotelResponse entityToResponse(Hotel entity){
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
