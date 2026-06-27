package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerUpdateDTO;
import br.com.rapidoja.tcc.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/email")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerResponseDTO> getCustomerByEmail(Authentication authentication) {
        String email = authentication.getName();
        return customerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            CustomerResponseDTO createdCustomer = customerService.create(customerRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(Authentication authentication, @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        String email = authentication.getName();
        try {
            CustomerResponseDTO updatedCustomer = customerService.update(email, customerUpdateDTO);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Void> deleteCustomer(Authentication authentication) {
        String email = authentication.getName();
        try {
            customerService.delete(email);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}