package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.auth.AuthRequestDTO;
import br.com.rapidoja.tcc.dto.auth.AuthResponseDTO;

public interface AuthServiceToken {
    AuthResponseDTO generateToken(AuthRequestDTO authRequestDTO);
}
