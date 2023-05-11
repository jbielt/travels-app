package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
