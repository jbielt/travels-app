package com.pim.projects.besttravel.domain.repository;

import com.pim.projects.besttravel.domain.entity.Fly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlyRepository extends JpaRepository<Fly, Long> {
}
