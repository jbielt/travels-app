package com.pim.projects.besttravel.api.controller;

import com.pim.projects.besttravel.api.model.request.ReservationRequest;
import com.pim.projects.besttravel.api.model.responses.ErrorsResponse;
import com.pim.projects.besttravel.api.model.responses.ReservationResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "reservation")
@AllArgsConstructor
@Tag(name = "Reservation")
public class ReservationController {

    private final IReservationService reservationService;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When the request have an invalid field, we response this",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
                    }
            )
        }
    )
    @Operation(summary = "Save a Reservation in the system based in a Hotel and the Customer")
    @PostMapping
    public ResponseEntity<ReservationResponse> post(@Valid @RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @Operation(summary = "Return a Reservation with the id sent by parameter")
    @GetMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.read(id));
    }

    @Operation(summary = "Update the Reservation")
    @PutMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> put(@Valid @PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.update(request, id));
    }

    @Operation(summary = "Delete the Reservation")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Return the Reservation price with the idHotel sent by parameter")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam Long hotelId, @RequestHeader(required = false)Currency currency){
        if(Objects.isNull(currency)) currency = Currency.getInstance("EUR");

        return ResponseEntity.ok(Collections.singletonMap("reservationPrice",this.reservationService.findPrice(hotelId, currency)));
    }
}
