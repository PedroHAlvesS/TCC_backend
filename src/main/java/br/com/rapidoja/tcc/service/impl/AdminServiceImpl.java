package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;
import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
import br.com.rapidoja.tcc.dto.admin.AdminUpdateDTO;
import br.com.rapidoja.tcc.mapper.AdminMapper;
import br.com.rapidoja.tcc.model.Profile;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.AdminRepository;
import br.com.rapidoja.tcc.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.rapidoja.tcc.util.Validate.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AdminResponseDTO> findByEmail(String email) {
        return adminRepository.findEnabledByEmail(email)
                .map(adminMapper::toResponseDTO);
    }

    @Override
    public AdminResponseDTO create(AdminRequestDTO adminRequestDTO) {
        if (adminRepository.existsByEmail(adminRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = adminMapper.toEntity(adminRequestDTO);
        user.setProfile(Profile.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = adminRepository.save(user);
        return adminMapper.toResponseDTO(savedUser);
    }

    @Override
    public AdminResponseDTO update(Long id, AdminUpdateDTO adminUpdateDTO) {
        User user = adminRepository.findEnabledById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        if (adminUpdateDTO.getEmail() != null && isValidEmail(adminUpdateDTO.getEmail())) {
            if (adminRepository.existsByEmailAndNotId(adminUpdateDTO.getEmail(), id)) {
                throw new IllegalArgumentException("Email already in use by another admin");
            }
            user.setEmail(adminUpdateDTO.getEmail());
        }

        if (adminUpdateDTO.getPassword() != null && isValidPassword(adminUpdateDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(adminUpdateDTO.getPassword()));
        }

        if (adminUpdateDTO.getPhoneNumber() != null && isValidPhone(adminUpdateDTO.getPhoneNumber())) {
            user.setPhoneNumber(adminUpdateDTO.getPhoneNumber());
        }

        User updatedUser = adminRepository.save(user);
        return adminMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = adminRepository.findEnabledById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        user.setEnabled(false);
        adminRepository.save(user);
    }
}
