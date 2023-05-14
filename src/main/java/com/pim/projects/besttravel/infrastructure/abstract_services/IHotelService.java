package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse>{

    Set<HotelResponse> readByRatingGreaterThan(Integer rating);


}
