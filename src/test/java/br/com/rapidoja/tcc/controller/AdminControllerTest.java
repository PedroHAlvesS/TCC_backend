package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;
import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
import br.com.rapidoja.tcc.dto.admin.AdminUpdateDTO;
import br.com.rapidoja.tcc.mocks.admin.AdminRequestMock;
import br.com.rapidoja.tcc.mocks.admin.AdminResponseMock;
import br.com.rapidoja.tcc.mocks.admin.AdminUpdateMock;
import br.com.rapidoja.tcc.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @MockitoBean
    private AdminService adminService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper =  new ObjectMapper();

    AdminController adminController;

    @BeforeEach
    void setUp() {
        adminController = new AdminController(adminService);
    }

    @Nested
    @DisplayName("Given get admin by email")
    class GivenGetAdminByEmail {
        @Nested
        @DisplayName("When admin by email is called with success")
        class WhenAdminByEmailIsCalledWithSuccess {
            private MvcResult result;
            private final AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();


            @BeforeEach
            void setUp() throws Exception {
                final String adminEmail = "teste@email.com";
                when(adminService.findByEmail(adminEmail)).thenReturn(Optional.of(adminResponseDTO));

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admins/email/"+ adminEmail).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return admin dto with success")
            void thenShouldReturnAdminDto() throws Exception {
                AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
                assertEquals(adminResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When admin by id is called with error")
        class WhenAdminByIdIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final String adminEmail = "teste@email.com";
                when(adminService.findByEmail(adminEmail)).thenReturn(Optional.empty());

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admins/email/"+ adminEmail).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 404")
            void thenShouldReturn404() {
                assertEquals(404, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given create admin")
    class GivenCreateAdmin {
        @Nested
        @DisplayName("When create admin called with success")
        class WhenAdminByEmailIsCalledWithSuccess {
            private MvcResult result;
            private final AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                final AdminRequestDTO adminRequestDTO = AdminRequestMock.getAdminRequestDTO();
                when(adminService.create(adminRequestDTO)).thenReturn(adminResponseDTO);

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/admins").header("Content-Type", "application/json").content(mapper.writeValueAsString(adminRequestDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return admin dto with success")
            void thenShouldReturnAdminDto() throws Exception {
                AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
                assertEquals(adminResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 201")
            void thenShouldReturn201() {
                assertEquals(201, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When create admin is called with error")
        class WhenCreateAdminIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final AdminRequestDTO adminRequestDTO = AdminRequestMock.getAdminRequestDTO();
                when(adminService.create(adminRequestDTO)).thenThrow(new IllegalArgumentException());

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/admins").header("Content-Type", "application/json").content(mapper.writeValueAsString(adminRequestDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return 400")
            void thenShouldReturn400() {
                assertEquals(400, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given update admin")
    class GivenUpdateAdmin {
        @Nested
        @DisplayName("When update admin called with success")
        class WhenUpdateAdminIsCalledWithSuccess {
            private MvcResult result;
            private final AdminResponseDTO adminResponseDTO = AdminResponseMock.getAdminResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                final AdminUpdateDTO adminUpdateDTO = AdminUpdateMock.getAdminUpdateDTO();
                final Long adminId = 1L;
                when(adminService.update(adminId, adminUpdateDTO)).thenReturn(adminResponseDTO);

                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admins/" + adminId).header("Content-Type", "application/json").content(mapper.writeValueAsString(adminUpdateDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return admin dto with success")
            void thenShouldReturnAdminDto() throws Exception {
                AdminResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), AdminResponseDTO.class);
                assertEquals(adminResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When update admin is called with error")
        class WhenUpdateAdminIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final AdminUpdateDTO adminUpdateDTO = AdminUpdateMock.getAdminUpdateDTO();
                final Long adminId = 1L;
                when(adminService.update(adminId, adminUpdateDTO)).thenThrow(new IllegalArgumentException());

                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admins/" + adminId).header("Content-Type", "application/json").content(mapper.writeValueAsString(adminUpdateDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return 400")
            void thenShouldReturn400() {
                assertEquals(400, result.getResponse().getStatus());
            }
        }

    }

    @Nested
    @DisplayName("Given delete admin")
    class GivenDeleteAdmin {
        @Nested
        @DisplayName("When delete admin called with success")
        class WhenDeleteAdminIsCalledWithSuccess {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final Long adminId = 1L;
                doNothing().when(adminService).delete(adminId);

                result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admins/" + adminId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 204")
            void thenShouldReturn204() {
                assertEquals(204, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When delete admin is called with error")
        class WhenDeleteAdminIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final Long adminId = 1L;

                doThrow(new IllegalArgumentException("Admin not found"))
                        .when(adminService)
                        .delete(adminId);

                result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admins/" + adminId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 404")
            void thenShouldReturn404() {
                assertEquals(404, result.getResponse().getStatus());
            }
        }
    }
}
