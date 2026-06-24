package br.com.rapidoja.tcc.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private Long customerId;
    private String customerName;
    private Long deliveryManId;
    private String deliveryManName;
    private String complement;
    private String description;
    private String observation;
    private String status;
    private LocalDateTime creationDate;
    private OrderAddressDTO address;
}
