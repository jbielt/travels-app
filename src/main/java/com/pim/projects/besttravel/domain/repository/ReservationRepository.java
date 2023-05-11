package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, UUID> {
}
