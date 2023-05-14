package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse>{

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
