package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;
import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateAssignDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateStatusDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> findAll();

    List<OrderResponseDTO> findByCustomerId(Long customerId);

    List<OrderResponseDTO> findByDeliveryManId(Long deliveryManId);

    OrderResponseDTO create(OrderRequestDTO orderRequestDTO, String email);

    OrderResponseDTO updateAssign(Long id, OrderUpdateAssignDTO orderUpdateAssignDTO);

    List<OrderResponseDTO> findByCustomerEmail(String email);

    List<OrderResponseDTO> findByDeliveryManEmail(String email);

    OrderResponseDTO updateStatusByDeliveryMan(Long id, OrderUpdateStatusDTO orderUpdateStatusDTO, String email);
}
