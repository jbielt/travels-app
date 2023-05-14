package com.pim.projects.besttravel.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "tour")
public class Tour implements Serializable {

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

    @PrePersist
    @PreRemove
    public void updateFk(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }

    public void removeTicket(UUID id){
        this.tickets.forEach(ticket -> {
            if(ticket.getId().equals(id)){
                ticket.setTour(null);
            }
        });
    }

    public void addTicket(Ticket ticket){
        if(Objects.isNull(this.tickets)){
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
        this.tickets.forEach(t -> t.setTour(this));
    }

}
