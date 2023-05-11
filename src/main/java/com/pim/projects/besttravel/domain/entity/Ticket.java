package com.pim.projects.besttravel.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "ticket")
public class Ticket {

    @Id
    private UUID id;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private LocalDate purchaseDate;

    private BigDecimal price;




}
