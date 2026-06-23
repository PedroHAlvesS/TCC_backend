package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.auth.AuthRequestDTO;
import br.com.rapidoja.tcc.dto.auth.AuthResponseDTO;
import br.com.rapidoja.tcc.mocks.auth.AuthRequestMock;
import br.com.rapidoja.tcc.mocks.auth.AuthResponseMock;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.model.Profile;
import br.com.rapidoja.tcc.repository.AdminRepository;
import br.com.rapidoja.tcc.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private AdminRepository adminRepository;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authenticationManager, tokenService, adminRepository);
    }

    @Nested
    @DisplayName("Given login is called")
    class GivenLoginIsCalled {
        @Nested
        @DisplayName("When login is successful")
        class WhenLoginIsSuccessful {
            private MvcResult result;
            private final AuthRequestDTO authRequestDTO = AuthRequestMock.getAuthRequestDTO();
            private final AuthResponseDTO authResponseDTO = AuthResponseMock.getAuthResponseDTO();
            private final User user = new User();

            @BeforeEach
            void setUp() throws Exception {
                user.setEmail("admin@rapidoja.com");
                user.setProfile(Profile.ADMIN);

                when(adminRepository.findByEmail(authRequestDTO.getEmail())).thenReturn(java.util.Optional.of(user));
                when(tokenService.generateToken(user.getEmail(), user.getProfile().name())).thenReturn("mock-jwt-token");

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(authRequestDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return auth response with token")
            void thenShouldReturnAuthResponse() throws Exception {
                AuthResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AuthResponseDTO.class);
                assertEquals("mock-jwt-token", resultDto.getToken());
                assertEquals("admin@rapidoja.com", resultDto.getEmail());
                assertEquals("ADMIN", resultDto.getProfile());
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When login fails with bad credentials")
        class WhenLoginFailsWithBadCredentials {
            private MvcResult result;
            private final AuthRequestDTO authRequestDTO = AuthRequestMock.getAuthRequestDTO();

            @BeforeEach
            void setUp() throws Exception {
                when(authenticationManager.authenticate(any())).thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(authRequestDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return 401")
            void thenShouldReturn401() {
                assertEquals(401, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When login fails with user not found")
        class WhenLoginFailsWithUserNotFound {
            private MvcResult result;
            private final AuthRequestDTO authRequestDTO = AuthRequestMock.getAuthRequestDTO();

            @BeforeEach
            void setUp() throws Exception {
                when(adminRepository.findByEmail(authRequestDTO.getEmail())).thenReturn(java.util.Optional.empty());

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(authRequestDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return 401")
            void thenShouldReturn401() {
                assertEquals(401, result.getResponse().getStatus());
            }
        }
    }
}
