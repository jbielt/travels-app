package com.pim.projects.besttravel.api.controller;

import com.pim.projects.besttravel.api.model.request.TicketRequest;
import com.pim.projects.besttravel.api.model.responses.ErrorsResponse;
import com.pim.projects.besttravel.api.model.responses.ReservationResponse;
import com.pim.projects.besttravel.api.model.responses.TicketResponse;
import com.pim.projects.besttravel.infrastructure.abstract_services.ITicketService;
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
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
@Tag(name = "Ticket")
public class TicketController {

    private final ITicketService ticketService;

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
    @Operation(summary = "Save a Ticket in the system based in a list of hotels, flights and customers")
    @PostMapping
    public ResponseEntity<TicketResponse> post(@Valid @RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @Operation(summary = "Return a Ticket with the id sent by parameter")
    @GetMapping(path = "{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }

    @Operation(summary = "Update the Flight Ticket")
    @PutMapping(path = "{id}")
    public ResponseEntity<TicketResponse> put(@Valid @PathVariable UUID id, @RequestBody TicketRequest request){
        return ResponseEntity.ok(this.ticketService.update(request, id));
    }

    @Operation(summary = "Delete a Ticket")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Return the Ticket Flight price with the id sent by parameter")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlightPrice(@RequestParam Long flightId){
        return ResponseEntity.ok(Collections.singletonMap("flightPrice",this.ticketService.findFlightPrice(flightId)));
    }

}
