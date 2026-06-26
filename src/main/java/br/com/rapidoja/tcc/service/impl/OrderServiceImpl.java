package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.order.*;
import br.com.rapidoja.tcc.mapper.OrderMapper;
import br.com.rapidoja.tcc.model.Address;
import br.com.rapidoja.tcc.model.Order;
import br.com.rapidoja.tcc.model.OrderStatus;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.DeliveryManRepository;
import br.com.rapidoja.tcc.repository.OrderRepository;
import br.com.rapidoja.tcc.repository.UserRepository;
import br.com.rapidoja.tcc.service.AddressService;
import br.com.rapidoja.tcc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final AddressService addressService;
    private final OrderMapper orderMapper;
    @Override
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponseDTO> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toResponseDTO);
    }

    @Override
    public List<OrderResponseDTO> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> findByDeliveryManId(Long deliveryManId) {
        return orderRepository.findByDeliveryManId(deliveryManId).stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> findByStatus(String status) {
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {
        User customer = userRepository.findById(orderRequestDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Address address = addressService.findOrCreate(
                orderRequestDTO.getAddress()
        );

        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(address);
        order.setDescription(orderRequestDTO.getDescription());
        order.setObservation(orderRequestDTO.getObservation());
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderUpdateDTO orderUpdateDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (orderUpdateDTO.getDescription() != null && !orderUpdateDTO.getDescription().isBlank()) {
            order.setDescription(orderUpdateDTO.getDescription());
        }

        if (orderUpdateDTO.getObservation() != null) {
            order.setObservation(orderUpdateDTO.getObservation());
        }

        if (orderUpdateDTO.getStatus() != null && !orderUpdateDTO.getStatus().isBlank()) {
            try {
                order.setStatus(OrderStatus.valueOf(orderUpdateDTO.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid order status: " + orderUpdateDTO.getStatus());
            }
        }

        if (orderUpdateDTO.getDeliveryManId() != null) {
            User deliveryMan = deliveryManRepository.findEnabledById(orderUpdateDTO.getDeliveryManId())
                    .orElseThrow(() -> new IllegalArgumentException("Delivery man not found"));
            order.setDeliveryMan(deliveryMan);
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(updatedOrder);
    }

    @Override
    public OrderResponseDTO updateAssign(Long id, OrderUpdateAssignDTO orderUpdateAssignDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        User deliveryMan = deliveryManRepository.findEnabledById(orderUpdateAssignDTO.getDeliveryManId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery man not found"));

        order.setDeliveryMan(deliveryMan);
        order.setStatus(OrderStatus.ASSIGNED);

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(updatedOrder);
    }

    @Override
    public List<OrderResponseDTO> findByCustomerEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        List<Order> orders = orderRepository.findByCustomerId(user.getId());
        return orders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> findByDeliveryManEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        List<Order> orders = orderRepository.findByDeliveryManId(user.getId());
        return orders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO updateStatus(Long id, OrderUpdateStatusDTO orderUpdateStatusDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        User deliveryMan = deliveryManRepository.findByEmail(orderUpdateStatusDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Delivery man not found"));

        order.setStatus(OrderStatus.valueOf(orderUpdateStatusDTO.getStatus()));
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(updatedOrder);
    }
}
