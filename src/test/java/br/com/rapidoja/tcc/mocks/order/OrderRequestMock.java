package br.com.rapidoja.tcc.mocks.order;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;

public class OrderRequestMock {
    public static OrderRequestDTO getOrderRequestDTO() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomerId(1L);
        orderRequestDTO.setDescription("Test order");
        orderRequestDTO.setObservation("Test observation");
        orderRequestDTO.setAddress(OrderAddressMock.getAddress());
        return orderRequestDTO;
    }
}
