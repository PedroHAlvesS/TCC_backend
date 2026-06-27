package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.auth.AuthRequestDTO;
import br.com.rapidoja.tcc.dto.auth.AuthResponseDTO;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.UserRepository;
import br.com.rapidoja.tcc.security.TokenService;
import br.com.rapidoja.tcc.service.AuthServiceToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceToken {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public AuthResponseDTO generateToken(AuthRequestDTO authRequestDTO) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                );

        authenticationManager.authenticate(usernamePassword);

        User user = userRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = tokenService.generateToken(user.getEmail(), user.getProfile().name(), user.getId());

        return new AuthResponseDTO(
                token,
                user.getEmail(),
                user.getProfile().name()
        );
    }
}
