package com.pim.projects.besttravel.infrastructure.services;

import com.pim.projects.besttravel.api.model.request.ReservationRequest;
import com.pim.projects.besttravel.api.model.responses.HotelResponse;
import com.pim.projects.besttravel.api.model.responses.ReservationResponse;
import com.pim.projects.besttravel.domain.entity.Reservation;
import com.pim.projects.besttravel.domain.repository.CustomerRepository;
import com.pim.projects.besttravel.domain.repository.HotelRepository;
import com.pim.projects.besttravel.domain.repository.ReservationRepository;
import com.pim.projects.besttravel.infrastructure.abstract_services.IReservationService;
import com.pim.projects.besttravel.infrastructure.helper.BlackListHelper;
import com.pim.projects.besttravel.infrastructure.helper.CustomerHelper;
import com.pim.projects.besttravel.util.enums.Tables;
import com.pim.projects.besttravel.util.exception.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CustomerHelper customerHelper;
    private BlackListHelper blackListHelper;

    public static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.20);

    @Override
    public ReservationResponse create(ReservationRequest request) {
        //validating is the customer is in black list
        blackListHelper.isBlackListCustomer(request.getIdCustomer());

        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        var customer = this.customerRepository.findById(request.getIdCustomer()).orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));

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

        //increase total lodgings
        this.customerHelper.increase(customer.getDni(), ReservationService.class);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id).orElseThrow();
        return entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(Tables.reservation.name()));
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
        var reservationToDelete = this.reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(Tables.reservation.name()));
        this.reservationRepository.delete(reservationToDelete);
    }

    @Override
    public BigDecimal findHotelPrice(Long hotelId) {
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
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
