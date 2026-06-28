package br.com.rapidoja.tcc.mocks.order;

import br.com.rapidoja.tcc.dto.order.OrderUpdateStatusDTO;

public class OrderUpdateStatusMock {
    public static OrderUpdateStatusDTO getOrderUpdateStatusDTO() {
        OrderUpdateStatusDTO dto = new OrderUpdateStatusDTO();
        dto.setStatus("IN_TRANSIT");
        return dto;
    }
}
