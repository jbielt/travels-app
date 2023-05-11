package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {
}
