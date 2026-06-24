package br.com.rapidoja.tcc.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddressDTO {
    @NotBlank(message = "Street is required")
    private String street;

    private String number;

    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    private String complement;
}
