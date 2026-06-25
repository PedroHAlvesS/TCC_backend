package br.com.rapidoja.tcc.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateAssignDTO {

    @NotNull
    private Long deliveryManId;
}
