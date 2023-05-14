package com.pim.projects.besttravel.infrastructure.helper;

import com.pim.projects.besttravel.domain.repository.ReservationRepository;
import com.pim.projects.besttravel.domain.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
}
