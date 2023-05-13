package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Set<Hotel> findByPriceLessThan(BigDecimal price);

    Set<Hotel> findByPriceIsBetween(BigDecimal min, BigDecimal max);

    Set<Hotel> findByRatingGreaterThan(Integer rating);

    Optional<Hotel> findByReservationsId(UUID id);
}
