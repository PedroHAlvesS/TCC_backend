package br.com.rapidoja.tcc.mocks.auth;

import br.com.rapidoja.tcc.dto.auth.AuthResponseDTO;

public class AuthResponseMock {
    public static AuthResponseDTO getAuthResponseDTO() {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken("mock-jwt-token");
        authResponseDTO.setEmail("admin@rapidoja.com");
        authResponseDTO.setProfile("ADMIN");
        return authResponseDTO;
    }
}
