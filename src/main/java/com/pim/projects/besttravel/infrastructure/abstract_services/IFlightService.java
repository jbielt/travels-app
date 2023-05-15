package com.pim.projects.besttravel.infrastructure.abstract_services;

import com.pim.projects.besttravel.api.model.responses.FlightResponse;

import java.util.Set;

public interface IFlightService extends CatalogService<FlightResponse>{

    Set<FlightResponse> readByOriginDestiny(String origin, String destiny);
}
