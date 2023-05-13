package com.pim.projects.besttravel.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<Reservation> reservations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;


    public void addTicket(Ticket ticket){
        if(Objects.isNull(this.tickets)){
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id){
        if(Objects.isNull(this.tickets)){
            this.tickets = new HashSet<>();
        }
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void updateTickets(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    public void addReservation(Reservation reservation){
        if(Objects.isNull(this.reservations)){
            this.reservations = new HashSet<>();
        }
        this.reservations.add(reservation);
    }

    public void removeReservation(UUID id){
        if(Objects.isNull(this.reservations)){
            this.reservations = new HashSet<>();
        }
        this.reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void updateReservations(){
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }
}
