package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;
import br.com.rapidoja.tcc.service.DeliveryManService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-men")
@RequiredArgsConstructor
public class DeliveryManController {

    private final DeliveryManService deliveryManService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DeliveryManResponseDTO>> getAllDeliveryMen() {
        List<DeliveryManResponseDTO> deliveryMen = deliveryManService.findAll();
        return ResponseEntity.ok(deliveryMen);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DeliveryManResponseDTO> createDeliveryMan(@Valid @RequestBody DeliveryManRequestDTO deliveryManRequestDTO) {
        try {
            DeliveryManResponseDTO createdDeliveryMan = deliveryManService.create(deliveryManRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeliveryMan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DeliveryManResponseDTO> updateDeliveryMan(@PathVariable Long id, @RequestBody DeliveryManUpdateDTO deliveryManUpdateDTO) {
        try {
            DeliveryManResponseDTO updatedDeliveryMan = deliveryManService.update(id, deliveryManUpdateDTO);
            return ResponseEntity.ok(updatedDeliveryMan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDeliveryMan(@PathVariable Long id) {
        try {
            deliveryManService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email")
    @PreAuthorize("hasRole('ROLE_DELIVERY_MAN')")
    public ResponseEntity<DeliveryManResponseDTO> getDeliveryManByEmail(Authentication authentication) {
        String email = authentication.getName();
        DeliveryManResponseDTO responseDTO = deliveryManService.findByEmail(email);
        return ResponseEntity.ok(responseDTO);
    }
}
