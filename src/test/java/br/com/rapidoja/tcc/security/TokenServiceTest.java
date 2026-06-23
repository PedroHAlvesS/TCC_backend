package br.com.rapidoja.tcc.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final Long EXPIRATION = 86400000L;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
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
            private final String email = "admin@rapidoja.com";
            private final String profile = "ADMIN";

            @BeforeEach
            void setUp() {
                token = tokenService.generateToken(email, profile);
            }

            @Test
            @DisplayName("Then should return a non-empty token")
            void thenShouldReturnANonEmptyToken() {
                assertNotNull(token);
                assertFalse(token.isEmpty());
            }

            @Test
            @DisplayName("Then token should contain email as subject")
            void thenTokenShouldContainEmailAsSubject() {
                String validatedEmail = tokenService.validateToken(token);
                assertEquals(email, validatedEmail);
            }

            @Test
            @DisplayName("Then token should contain profile")
            void thenTokenShouldContainProfile() {
                String profileFromToken = tokenService.getProfileFromToken(token);
                assertEquals(profile, profileFromToken);
            }
        }
    }

    @Nested
    @DisplayName("Given validateToken is called")
    class GivenValidateTokenIsCalled {
        @Nested
        @DisplayName("When validateToken is valid")
        class WhenValidateTokenIsValid {
            private String token;
            private final String email = "admin@rapidoja.com";
            private final String profile = "ADMIN";

            @BeforeEach
            void setUp() {
                token = tokenService.generateToken(email, profile);
            }

            @Test
            @DisplayName("Then should return email")
            void thenShouldReturnEmail() {
                String result = tokenService.validateToken(token);
                assertEquals(email, result);
            }
        }

        @Nested
        @DisplayName("When validateToken is invalid")
        class WhenValidateTokenIsInvalid {
            private final String invalidToken = "invalid.token.here";

            @Test
            @DisplayName("Then should return null")
            void thenShouldReturnNull() {
                String result = tokenService.validateToken(invalidToken);
                assertNull(result);
            }
        }
    }

    @Nested
    @DisplayName("Given getProfileFromToken is called")
    class GivenGetProfileFromTokenIsCalled {
        @Nested
        @DisplayName("When getProfileFromToken is valid")
        class WhenGetProfileFromTokenIsValid {
            private String token;
            private final String email = "admin@rapidoja.com";
            private final String profile = "ADMIN";

            @BeforeEach
            void setUp() {
                token = tokenService.generateToken(email, profile);
            }

            @Test
            @DisplayName("Then should return profile")
            void thenShouldReturnProfile() {
                String result = tokenService.getProfileFromToken(token);
                assertEquals(profile, result);
            }
        }

        @Nested
        @DisplayName("When getProfileFromToken is invalid")
        class WhenGetProfileFromTokenIsInvalid {
            private final String invalidToken = "invalid.token.here";

            @Test
            @DisplayName("Then should return null")
            void thenShouldReturnNull() {
                String result = tokenService.getProfileFromToken(invalidToken);
                assertNull(result);
            }
        }
    }
}
