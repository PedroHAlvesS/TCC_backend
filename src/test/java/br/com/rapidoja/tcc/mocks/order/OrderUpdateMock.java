package br.com.rapidoja.tcc.mocks.order;

import br.com.rapidoja.tcc.dto.order.OrderUpdateDTO;

public class OrderUpdateMock {
    public static OrderUpdateDTO getOrderUpdateDTO() {
        OrderUpdateDTO orderUpdateDTO = new OrderUpdateDTO();
        orderUpdateDTO.setDescription("Updated order description");
        orderUpdateDTO.setObservation("Updated observation");
        orderUpdateDTO.setStatus("IN_TRANSIT");
        orderUpdateDTO.setDeliveryManId(2L);
        return orderUpdateDTO;
    }
}
