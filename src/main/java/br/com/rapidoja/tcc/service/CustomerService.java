package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerResponseDTO> findAll();

    Optional<CustomerResponseDTO> findById(Long id);

    Optional<CustomerResponseDTO> findByEmail(String email);

    CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO update(Long id, CustomerUpdateDTO customerUpdateDTO);

    void delete(Long id);
}
