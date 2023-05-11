package com.pim.projects.besttravel.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "customer")
public class Customer {

    @Id
    private String dni;

    @Column(length = 50)
    private String fullName;

    @Column(length = 20)
    private String creditCard;

    @Column(length = 12)
    private String phoneNumber;

    private Integer totalFlights;

    private Integer totalLodgings;

    private Integer totalTours;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private Set<Ticket> tickets;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private Set<Reservation> reservations;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "customer"
    )
    private Set<Tour> tours;

}
