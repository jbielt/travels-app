package com.pim.projects.besttravel.domain.entity;

import com.pim.projects.besttravel.util.Airline;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data //include getters, setters(non-final fields), toString, RequiredArgsConstructor and EqualsAndHashCode for mutable classes
@Builder //creates automatically a builder for our class with all the properties
@Entity(name = "fly")
public class Fly implements Serializable {

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "fly"
    )
    private Set<Ticket> tickets;

}
