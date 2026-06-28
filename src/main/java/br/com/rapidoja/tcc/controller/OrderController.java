package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.order.*;
import br.com.rapidoja.tcc.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // admin
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderResponseDTO> orders = orderService.findByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    // admin and delivery man
    @GetMapping("/delivery-man/{deliveryManId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByDeliveryManId(@PathVariable Long deliveryManId) {
        List<OrderResponseDTO> orders = orderService.findByDeliveryManId(deliveryManId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(@PathVariable String status) {
        List<OrderResponseDTO> orders = orderService.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            OrderResponseDTO createdOrder = orderService.create(orderRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateDTO orderUpdateDTO) {
        try {
            OrderResponseDTO updatedOrder = orderService.update(id, orderUpdateDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/assign")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderResponseDTO> assignOrder(@PathVariable Long id, @RequestBody OrderUpdateAssignDTO orderUpdateAssignDTO) {
        try {
            OrderResponseDTO updatedOrder = orderService.updateAssign(id, orderUpdateAssignDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomerEmail(Authentication authentication) {
        String email = authentication.getName();
        List<OrderResponseDTO> orders = orderService.findByCustomerEmail(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/delivery-man")
    @PreAuthorize("hasRole('ROLE_DELIVERY_MAN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByDeliveryManEmail(Authentication authentication) {
        String email = authentication.getName();
        List<OrderResponseDTO> orders = orderService.findByDeliveryManEmail(email);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/delivery-man/{id}/status")
    @PreAuthorize("hasRole('ROLE_DELIVERY_MAN')")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(Authentication authentication, @PathVariable Long id, @RequestBody OrderUpdateStatusDTO orderUpdateStatusDTO) {
        String email = authentication.getName();
        try {
            OrderResponseDTO updatedOrder = orderService.updateStatusByDeliveryMan(id, orderUpdateStatusDTO, email);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
