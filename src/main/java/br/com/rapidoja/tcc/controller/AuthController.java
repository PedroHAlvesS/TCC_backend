package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.auth.AuthRequestDTO;
import br.com.rapidoja.tcc.dto.auth.AuthResponseDTO;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.UserRepository;
import br.com.rapidoja.tcc.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        try {
            UsernamePasswordAuthenticationToken usernamePassword = 
                new UsernamePasswordAuthenticationToken(
                    authRequestDTO.getEmail(), 
                    authRequestDTO.getPassword()
                );
            
            authenticationManager.authenticate(usernamePassword);
            
            User user = userRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            String token = tokenService.generateToken(user.getEmail(), user.getProfile().name());
            
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                token,
                user.getEmail(),
                user.getProfile().name()
            );
            
            return ResponseEntity.ok(authResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
