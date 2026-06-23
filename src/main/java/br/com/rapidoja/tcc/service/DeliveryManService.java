package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface DeliveryManService {

    List<DeliveryManResponseDTO> findAll();

    Optional<DeliveryManResponseDTO> findById(Long id);

    Optional<DeliveryManResponseDTO> findByEmail(String email);

    DeliveryManResponseDTO create(DeliveryManRequestDTO deliveryManRequestDTO);

    DeliveryManResponseDTO update(Long id, DeliveryManUpdateDTO deliveryManUpdateDTO);

    void delete(Long id);
}
