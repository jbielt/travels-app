package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Fly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface FlyRepository extends JpaRepository<Fly, Long> {

    //We'll use JPQL for the queries

    @Query("SELECT f FROM fly f WHERE f.price < :price")
    Set<Fly> findByPriceLessThan(BigDecimal price);

    @Query("SELECT f FROM fly f WHERE f.price BETWEEN :min and :max")
    Set<Fly> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("SELECT f FROM fly f WHERE f.originName = :origin AND f.destinyName = :destiny")
    Set<Fly> findByOriginAndDestiny(String origin, String destiny);

    @Query("SELECT f FROM fly f JOIN FETCH f.tickets t WHERE t.id = :id")
    Optional<Fly> findByTicketId(UUID id);
}
