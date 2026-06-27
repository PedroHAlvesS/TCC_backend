package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;
import br.com.rapidoja.tcc.mapper.DeliveryManMapper;
import br.com.rapidoja.tcc.model.Profile;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.DeliveryManRepository;
import br.com.rapidoja.tcc.service.DeliveryManService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.rapidoja.tcc.util.Validate.*;

@Service
@RequiredArgsConstructor
public class DeliveryManServiceImpl implements DeliveryManService {

    private final DeliveryManRepository deliveryManRepository;
    private final DeliveryManMapper deliveryManMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<DeliveryManResponseDTO> findAll() {
        return deliveryManRepository.findAllEnabled().stream()
                .map(deliveryManMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryManResponseDTO create(DeliveryManRequestDTO deliveryManRequestDTO) {
        if (deliveryManRepository.existsByEmail(deliveryManRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = deliveryManMapper.toEntity(deliveryManRequestDTO);
        user.setProfile(Profile.DELIVERY_MAN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = deliveryManRepository.save(user);
        return deliveryManMapper.toResponseDTO(savedUser);
    }

    @Override
    public DeliveryManResponseDTO update(Long id, DeliveryManUpdateDTO deliveryManUpdateDTO) {
        User user = deliveryManRepository.findEnabledById(id)
                .orElseThrow(() -> new IllegalArgumentException("DeliveryMan not found"));

        if (deliveryManUpdateDTO.getEmail() != null && isValidEmail(deliveryManUpdateDTO.getEmail())) {
            if (deliveryManRepository.existsByEmailAndNotId(deliveryManUpdateDTO.getEmail(), id)) {
                throw new IllegalArgumentException("Email already in use by another delivery man");
            }
            user.setEmail(deliveryManUpdateDTO.getEmail());
        }

        if (deliveryManUpdateDTO.getPassword() != null && isValidPassword(deliveryManUpdateDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(deliveryManUpdateDTO.getPassword()));
        }

        if (deliveryManUpdateDTO.getPhoneNumber() != null && isValidPhone(deliveryManUpdateDTO.getPhoneNumber())) {
            user.setPhoneNumber(deliveryManUpdateDTO.getPhoneNumber());
        }

        User updatedUser = deliveryManRepository.save(user);
        return deliveryManMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = deliveryManRepository.findEnabledById(id)
                .orElseThrow(() -> new IllegalArgumentException("DeliveryMan not found"));

        user.setEnabled(false);
        deliveryManRepository.save(user);
    }
}
