package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.order.*;

import java.util.List;
import java.util.Optional;

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
