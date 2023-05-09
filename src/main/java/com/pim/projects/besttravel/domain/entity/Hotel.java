package com.pim.projects.besttravel.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String address;

    private Integer rating;

    private BigDecimal price;


}
