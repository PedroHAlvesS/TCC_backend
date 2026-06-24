package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;
import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponseDTO> findAll();

    Optional<OrderResponseDTO> findById(Long id);

    List<OrderResponseDTO> findByCustomerId(Long customerId);

    List<OrderResponseDTO> findByDeliveryManId(Long deliveryManId);

    List<OrderResponseDTO> findByStatus(String status);

    OrderResponseDTO create(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO update(Long id, OrderUpdateDTO orderUpdateDTO);
}
