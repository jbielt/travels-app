package com.pim.projects.besttravel.api.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest implements Serializable {

    @Size(min = 18, max = 20, message = "The size must be between 18 and 20 characters")
    @NotBlank(message = "Id Customer is mandatory")
    private String idCustomer;

    @Positive
    @NotNull(message = "Id Hotel is mandatory")
    private Long idHotel;

    @Min(value = 1, message = "You have to stay at least one day for your reservation")
    @Max(value = 30, message = "You can only stay a maximum of 30 days for your reservation")
    @NotNull(message = "Total days is mandatory")
    private Integer totalDays;

    //@Pattern(regexp = "^(.+)@(.+)$")
    @Email(message = "Invalid email")
    private String email;
}
