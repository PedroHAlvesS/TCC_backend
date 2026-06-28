package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;

import java.util.List;

public interface DeliveryManService {

    List<DeliveryManResponseDTO> findAll();

    DeliveryManResponseDTO create(DeliveryManRequestDTO deliveryManRequestDTO);

    DeliveryManResponseDTO update(Long id, DeliveryManUpdateDTO deliveryManUpdateDTO);

    void delete(Long id);

    DeliveryManResponseDTO findByEmail(String email);
}
