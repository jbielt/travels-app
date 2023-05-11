package com.pim.projects.besttravel.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "reservation")
public class Reservation {

    @Id
    private UUID id;

    @Column(name = "date_reservation")
    private LocalDateTime dateTimeReservation;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private Integer totalDays;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;






}
