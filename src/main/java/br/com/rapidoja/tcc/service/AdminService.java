package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;
import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
import br.com.rapidoja.tcc.dto.admin.AdminUpdateDTO;

import java.util.Optional;

public interface AdminService {

    Optional<AdminResponseDTO> findByEmail(String email);

    AdminResponseDTO create(AdminRequestDTO adminRequestDTO);

    AdminResponseDTO update(String email, AdminUpdateDTO adminUpdateDTO);

}
