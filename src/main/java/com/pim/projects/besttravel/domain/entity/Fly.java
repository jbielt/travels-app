package com.pim.projects.besttravel.domain.entity;

import com.pim.projects.besttravel.util.Airline;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity(name = "fly")
public class Fly {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double originLat;

    private Double originLng;

    private Double destinyLat;

    private Double destinyLng;

    private BigDecimal price;

    @Column(length = 20)
    private String originName;

    @Column(length = 20)
    private String destinyName;

    @Enumerated(EnumType.STRING)
    private Airline airline;

}
