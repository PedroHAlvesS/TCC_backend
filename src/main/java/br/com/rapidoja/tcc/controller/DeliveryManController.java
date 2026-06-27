package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;
import br.com.rapidoja.tcc.service.DeliveryManService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-men")
@RequiredArgsConstructor
public class DeliveryManController {

    private final DeliveryManService deliveryManService;

    @GetMapping
    public ResponseEntity<List<DeliveryManResponseDTO>> getAllDeliveryMen() {
        List<DeliveryManResponseDTO> deliveryMen = deliveryManService.findAll();
        return ResponseEntity.ok(deliveryMen);
    }

    // nao usado por admin
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryManResponseDTO> getDeliveryManById(@PathVariable Long id) {
        return deliveryManService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DeliveryManResponseDTO> getDeliveryManByEmail(@PathVariable String email) {
        return deliveryManService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DeliveryManResponseDTO> createDeliveryMan(@Valid @RequestBody DeliveryManRequestDTO deliveryManRequestDTO) {
        try {
            DeliveryManResponseDTO createdDeliveryMan = deliveryManService.create(deliveryManRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeliveryMan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryManResponseDTO> updateDeliveryMan(@PathVariable Long id, @RequestBody DeliveryManUpdateDTO deliveryManUpdateDTO) {
        try {
            DeliveryManResponseDTO updatedDeliveryMan = deliveryManService.update(id, deliveryManUpdateDTO);
            return ResponseEntity.ok(updatedDeliveryMan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryMan(@PathVariable Long id) {
        try {
            deliveryManService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
