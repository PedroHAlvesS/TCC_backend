package br.com.rapidoja.tcc.mocks.order;

import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseMock {
    public static OrderResponseDTO getOrderResponseDTO() {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(1L);
        orderResponseDTO.setCustomerId(1L);
        orderResponseDTO.setCustomerName("John Doe");
        orderResponseDTO.setDeliveryManId(2L);
        orderResponseDTO.setDeliveryManName("Jane Delivery");
        orderResponseDTO.setComplement("Apto 1");
        orderResponseDTO.setDescription("Test order");
        orderResponseDTO.setObservation("Test observation");
        orderResponseDTO.setStatus("PENDING");
        orderResponseDTO.setCreationDate(LocalDateTime.now());
        orderResponseDTO.setAddress(OrderAddressMock.getAddress());
        return orderResponseDTO;
    }

    public static List<OrderResponseDTO> getOrderResponseList() {
        List<OrderResponseDTO> orderList = new ArrayList<>();
        orderList.add(getOrderResponseDTO());
        return orderList;
    }
}
