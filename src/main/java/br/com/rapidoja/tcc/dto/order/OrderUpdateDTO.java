package br.com.rapidoja.tcc.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {

    private String description;
    private String observation;
    private String status;
    private Long deliveryManId;
}
