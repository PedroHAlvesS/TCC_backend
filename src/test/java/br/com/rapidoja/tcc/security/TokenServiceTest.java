package br.com.rapidoja.tcc.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private TokenService tokenService;

    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final Long EXPIRATION = 86400000L;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService(userDetailsService);
        tokenService.setSecret(SECRET);
        tokenService.setExpiration(EXPIRATION);
    }

    @Nested
    @DisplayName("Given generateToken is called")
    class GivenGenerateTokenIsCalled {
        @Nested
        @DisplayName("When generateToken is valid")
        class WhenGenerateTokenIsValid {
            private String token;

            @BeforeEach
            void setUp() {
                final String email = "admin@rapidoja.com";
                final String profile = "ADMIN";
                final Long userId = 1L;
                token = tokenService.generateToken(email, profile, userId);
            }

            @Test
            @DisplayName("Then should return a non-empty token")
            void thenShouldReturnANonEmptyToken() {
                assertNotNull(token);
                assertFalse(token.isEmpty());
            }
        }
    }

    @Nested
    @DisplayName("Given getAuthentication is called")
    class GivenGetAuthenticationIsCalled {
        @Nested
        @DisplayName("When getAuthentication is valid")
        class WhenGetAuthenticationIsValid {
            private final String email = "admin@rapidoja.com";
            private Authentication result;

            @BeforeEach
            void setUp() {
                String token;
                final String profile = "ADMIN";
                final Long userId = 1L;
                UserDetails userDetails = User.builder()
                        .username(email)
                        .password("password")
                        .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        .build();

                when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

                token = tokenService.generateToken(email, profile, userId);
                result = tokenService.getAuthentication(token);
            }

            @Test
            @DisplayName("Then should return authentication")
            void thenShouldReturnAuthentication() {
                assertNotNull(result);
                assertEquals(email, result.getName());
            }

            @Test
            @DisplayName("Then should call userDetailsService")
            void thenShouldCallUserDetailsService() {
                verify(userDetailsService).loadUserByUsername(email);
            }
        }
    }
}
