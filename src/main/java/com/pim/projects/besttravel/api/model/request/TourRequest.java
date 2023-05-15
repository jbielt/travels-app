package com.pim.projects.besttravel.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {

    @Size(min = 18, max = 20, message = "The size must be between 18 and 20 characters")
    @NotBlank(message = "Id customer is mandatory")
    public String customerId;

    @Size(min = 1, message = "Min flight tour per tour")
    private Set<TourFlightRequest> flights;

    @Size(min = 1, message = "Min hotel tour per tour")
    private Set<TourHotelRequest> hotels;

}
