package br.com.rapidoja.tcc.mocks.auth;

import br.com.rapidoja.tcc.dto.auth.AuthRequestDTO;

public class AuthRequestMock {
    public static AuthRequestDTO getAuthRequestDTO() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setEmail("admin@rapidoja.com");
        authRequestDTO.setPassword("admin123");
        return authRequestDTO;
    }
}
