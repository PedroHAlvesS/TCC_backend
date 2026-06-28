package br.com.rapidoja.tcc.mocks.order;

import br.com.rapidoja.tcc.dto.order.OrderUpdateAssignDTO;

public class OrderUpdateAssignMock {
    public static OrderUpdateAssignDTO getOrderUpdateAssignDTO() {
        OrderUpdateAssignDTO dto = new OrderUpdateAssignDTO();
        dto.setDeliveryManId(1L);
        return dto;
    }
}
