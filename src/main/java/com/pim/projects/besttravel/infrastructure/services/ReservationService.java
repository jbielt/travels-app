package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.request.ReservationRequest;
import com.pim.projects.besttravel.api.model.responses.FlyResponse;
import com.pim.projects.besttravel.api.model.responses.HotelResponse;
import com.pim.projects.besttravel.api.model.responses.ReservationResponse;
import com.pim.projects.besttravel.api.model.responses.TicketResponse;
import com.pim.projects.besttravel.domain.entity.Reservation;
import com.pim.projects.besttravel.domain.entity.Ticket;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.HotelRepository;
import com.pim.projects.besttravel.domain.repository.ReservationRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.IReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    private static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.20);

    @Override
    public ReservationResponse create(ReservationRequest request) {
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();

        var reservationToPersist = Reservation.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
                .build();

        var reservationPersisted = reservationRepository.save(reservationToPersist);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id).orElseThrow();
        return entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow();
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));

        var reservationUpdated = reservationRepository.save(reservationToUpdate);

        log.info("Reservation updated with id {}", reservationUpdated.getId());

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = this.reservationRepository.findById(id).orElseThrow();
        this.reservationRepository.delete(reservationToDelete);
    }

    @Override
    public BigDecimal findHotelPrice(Long hotelId) {
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
    }



    private ReservationResponse entityToResponse(Reservation reservationEntity){
        var response = new ReservationResponse();

        //mapping entity to DTO
        BeanUtils.copyProperties(reservationEntity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(reservationEntity.getHotel(), hotelResponse);

        response.setHotel(hotelResponse);

        return response;
    }
}
