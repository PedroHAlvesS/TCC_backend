//package br.com.rapidoja.tcc.controller;
//
//import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;
//import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
//import br.com.rapidoja.tcc.dto.admin.AdminUpdateDTO;
//import br.com.rapidoja.tcc.mocks.admin.AdminRequestMock;
//import br.com.rapidoja.tcc.mocks.admin.AdminResponseMock;
//import br.com.rapidoja.tcc.mocks.admin.AdminUpdateMock;
//import br.com.rapidoja.tcc.security.TokenService;
//import br.com.rapidoja.tcc.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import tools.jackson.databind.ObjectMapper;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(AdminController.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
//class AdminControllerTest {
//
//    @MockitoBean
//    private AdminService adminService;
//
//    @MockitoBean
//    private TokenService tokenService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    ObjectMapper mapper =  new ObjectMapper();
//
//    AdminController adminController;
//
//    @BeforeEach
//    void setUp() {
//        adminController = new AdminController(adminService);
//    }
//
//    @Test
//    @DisplayName("When admin by email is called with success - Single Test")
//    @WithMockUser(username = "teste@email.com", authorities = {"ROLE_ADMIN"})
//    void shouldReturnAdminByEmail() throws Exception {
//        String adminEmail = "teste@email.com";
//        AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();
//
//        Authentication mockAuth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
//                adminEmail,
//                null,
//                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
//        );
//
//        when(tokenService.getAuthentication("mockToken")).thenReturn(mockAuth);
//        when(adminService.findByEmail(adminEmail)).thenReturn(Optional.of(adminResponseDTO));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/admins")
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer mockToken"))
//                .andExpect(result -> {
//                    AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
//                    assertEquals(adminResponseDTO, resultDto);
//                    assertEquals(200, result.getResponse().getStatus());
//                });
//    }
//
//    @Nested
//    @DisplayName("Given get admin by email")
//    class GivenGetAdminByEmail {
//        final String adminEmail = "teste@email.com";
//
//        @Nested
//        @DisplayName("When admin by email is called with success")
//        @WithMockUser(username = adminEmail, authorities = {"ROLE_ADMIN"})
//        class WhenAdminByEmailIsCalledWithSuccess {
//            private MvcResult result;
//            private final AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();
//
//            @BeforeEach
//            @WithMockUser(username = adminEmail, authorities = {"ROLE_ADMIN"})
//            void setUp() throws Exception {
//                when(adminService.findByEmail(adminEmail)).thenReturn(Optional.of(adminResponseDTO));
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admins").header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return admin dto with success")
//            @WithMockUser(username = adminEmail, authorities = {"ROLE_ADMIN"})
//            void thenShouldReturnAdminDto() throws Exception {
//                AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
//                assertEquals(adminResponseDTO, resultDto);
//            }
//
//            @Test
//            @DisplayName("Then should return 200")
//            void thenShouldReturn200() {
//                assertEquals(200, result.getResponse().getStatus());
//            }
//        }
//
//        @Nested
//        @DisplayName("When admin by id is called with error")
//        @WithMockUser(username = adminEmail, authorities = {"ROLE_ADMIN"})
//        class WhenAdminByIdIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                when(adminService.findByEmail(adminEmail)).thenReturn(Optional.empty());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admins").header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return 404")
//            void thenShouldReturn404() {
//                assertEquals(404, result.getResponse().getStatus());
//            }
//        }
//    }
//
//    @Nested
//    @DisplayName("Given create admin")
//    class GivenCreateAdmin {
//        @Nested
//        @DisplayName("When create admin called with success")
//        @WithMockUser(username = "admin@test.com", authorities = {"ROLE_ADMIN"})
//        class WhenAdminByEmailIsCalledWithSuccess {
//            private MvcResult result;
//            private final AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final AdminRequestDTO adminRequestDTO = AdminRequestMock.getAdminRequestDTO();
//                when(adminService.create(adminRequestDTO)).thenReturn(adminResponseDTO);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/admins").header("Content-Type", "application/json").content(mapper.writeValueAsString(adminRequestDTO))).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return admin dto with success")
//            void thenShouldReturnAdminDto() throws Exception {
//                AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
//                assertEquals(adminResponseDTO, resultDto);
//            }
//
//            @Test
//            @DisplayName("Then should return 201")
//            void thenShouldReturn201() {
//                assertEquals(201, result.getResponse().getStatus());
//            }
//        }
//
//        @Nested
//        @DisplayName("When create admin is called with error")
//        @WithMockUser(username = "admin@test.com", authorities = {"ROLE_ADMIN"})
//        class WhenCreateAdminIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final AdminRequestDTO adminRequestDTO = AdminRequestMock.getAdminRequestDTO();
//                when(adminService.create(adminRequestDTO)).thenThrow(new IllegalArgumentException());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/admins").header("Content-Type", "application/json").content(mapper.writeValueAsString(adminRequestDTO))).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return 400")
//            void thenShouldReturn400() {
//                assertEquals(400, result.getResponse().getStatus());
//            }
//        }
//    }
//
//    @Nested
//    @DisplayName("Given update admin")
//    class GivenUpdateAdmin {
//        @Nested
//        @DisplayName("When update admin called with success")
//        @WithMockUser(username = "email", authorities = {"ROLE_ADMIN"})
//        class WhenUpdateAdminIsCalledWithSuccess {
//            private MvcResult result;
//            private final AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final AdminUpdateDTO adminUpdateDTO = AdminUpdateMock.getAdminUpdateDTO();
//                String email = "email";
//                when(adminService.update(email, adminUpdateDTO)).thenReturn(adminResponseDTO);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admins/").header("Content-Type", "application/json").content(mapper.writeValueAsString(adminUpdateDTO))).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return admin dto with success")
//            void thenShouldReturnAdminDto() throws Exception {
//                AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
//                assertEquals(adminResponseDTO, resultDto);
//            }
//
//            @Test
//            @DisplayName("Then should return 200")
//            void thenShouldReturn200() {
//                assertEquals(200, result.getResponse().getStatus());
//            }
//        }
//
//        @Nested
//        @DisplayName("When update admin is called with error")
//        @WithMockUser(username = "email", authorities = {"ROLE_ADMIN"})
//        class WhenUpdateAdminIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final AdminUpdateDTO adminUpdateDTO = AdminUpdateMock.getAdminUpdateDTO();
//                String email = "email";
//                when(adminService.update(email, adminUpdateDTO)).thenThrow(new IllegalArgumentException());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admins/").header("Content-Type", "application/json").content(mapper.writeValueAsString(adminUpdateDTO))).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return 400")
//            void thenShouldReturn400() {
//                assertEquals(400, result.getResponse().getStatus());
//            }
//        }
//
//    }
//}
