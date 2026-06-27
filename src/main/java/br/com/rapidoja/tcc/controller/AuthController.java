package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.auth.AuthRequestDTO;
import br.com.rapidoja.tcc.dto.auth.AuthResponseDTO;
import br.com.rapidoja.tcc.service.AuthServiceToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceToken authServiceToken;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        try {
            AuthResponseDTO authResponseDTO = authServiceToken.generateToken(authRequestDTO);
            return ResponseEntity.ok(authResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
