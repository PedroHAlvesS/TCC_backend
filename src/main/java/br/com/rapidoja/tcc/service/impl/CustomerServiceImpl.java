package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerUpdateDTO;
import br.com.rapidoja.tcc.mapper.CustomerMapper;
import br.com.rapidoja.tcc.model.Profile;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.CustomerRepository;
import br.com.rapidoja.tcc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.rapidoja.tcc.util.Validate.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAllEnabled().stream()
                .map(customerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerResponseDTO> findByEmail(String email) {
        return customerRepository.findEnabledByEmail(email)
                .map(customerMapper::toResponseDTO);
    }

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO) {
        if (customerRepository.existsByEmail(customerRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = customerMapper.toEntity(customerRequestDTO);
        user.setProfile(Profile.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = customerRepository.save(user);
        return customerMapper.toResponseDTO(savedUser);
    }

    @Override
    public CustomerResponseDTO update(String email, CustomerUpdateDTO customerUpdateDTO) {
        User user = customerRepository.findEnabledByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (customerUpdateDTO.getEmail() != null && isValidEmail(customerUpdateDTO.getEmail())) {
            if (customerRepository.existsByEmail(customerUpdateDTO.getEmail())) {
                throw new IllegalArgumentException("Email already in use by another customer");
            }
            user.setEmail(customerUpdateDTO.getEmail());
        }

        if (customerUpdateDTO.getPassword() != null && isValidPassword(customerUpdateDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(customerUpdateDTO.getPassword()));
        }

        if (customerUpdateDTO.getPhoneNumber() != null && isValidPhone(customerUpdateDTO.getPhoneNumber())) {
            user.setPhoneNumber(customerUpdateDTO.getPhoneNumber());
        }

        User updatedUser = customerRepository.save(user);
        return customerMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void delete(String email) {
        User user = customerRepository.findEnabledByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        user.setEnabled(false);
        customerRepository.save(user);
    }
}