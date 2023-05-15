package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    //We'll use JPQL for the queries

    @Query("SELECT f FROM flight f WHERE f.price < :price")
    Set<Flight> findByPriceLessThan(BigDecimal price);

    @Query("SELECT f FROM flight f WHERE f.price BETWEEN :min and :max")
    Set<Flight> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("SELECT f FROM flight f WHERE f.originName = :origin AND f.destinyName = :destiny")
    Set<Flight> findByOriginAndDestiny(String origin, String destiny);

    @Query("SELECT f FROM flight f JOIN FETCH f.tickets t WHERE t.id = :id")
    Optional<Flight> findByTicketId(UUID id);
}
