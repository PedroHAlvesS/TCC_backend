package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;
import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
import br.com.rapidoja.tcc.dto.admin.AdminUpdateDTO;
import br.com.rapidoja.tcc.mapper.AdminMapper;
import br.com.rapidoja.tcc.mocks.UserMock;
import br.com.rapidoja.tcc.mocks.admin.AdminRequestMock;
import br.com.rapidoja.tcc.mocks.admin.AdminResponseMock;
import br.com.rapidoja.tcc.mocks.admin.AdminUpdateMock;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Nested
    @DisplayName("Given findByEmail is called")
    class GivenFindByEmailIsCalled {
        @Nested
        @DisplayName("When findByEmail is valid")
        class WhenFindByEmailIsValid {
            final String email = "test@email.com";
            final AdminResponseDTO adminResponse = AdminResponseMock.getAdminResponseDTO();
            final User user = UserMock.getUser();
            private Optional<AdminResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(adminRepository.findEnabledByEmail(email)).thenReturn(Optional.of(user));
                when(adminMapper.toResponseDTO(user)).thenReturn(adminResponse);

                result = adminService.findByEmail(email);
            }

            @Test
            @DisplayName("Then should return admin")
            void thenShouldReturnAdmin() {
                assertTrue(result.isPresent());
                assertEquals(adminResponse, result.get());
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(adminRepository).findEnabledByEmail(email);
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(adminMapper).toResponseDTO(user);
            }
        }
    }

    @Nested
    @DisplayName("Given create is called")
    class GivenCreateIsCalled {
        @Nested
        @DisplayName("When create is valid")
        class WhenCreateIsValid {
            final AdminRequestDTO adminRequestDTO = AdminRequestMock.getAdminRequestDTO();
            final AdminResponseDTO adminResponse = AdminResponseMock.getAdminResponseDTO();
            final User user = UserMock.getUser();
            private AdminResponseDTO result;

            @BeforeEach
            void setUp() {

                when(adminRepository.existsByEmail(adminRequestDTO.getEmail())).thenReturn(false);
                when(adminMapper.toEntity(adminRequestDTO)).thenReturn(user);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(adminRepository.save(user)).thenReturn(user);
                when(adminMapper.toResponseDTO(user)).thenReturn(adminResponse);

                result = adminService.create(adminRequestDTO);
            }

            @Test
            @DisplayName("Then should return created admin")
            void thenShouldReturnCreatedAdmin() {
                assertEquals(adminResponse, result);
            }

            @Test
            @DisplayName("Then should check if email exists")
            void thenShouldCheckIfEmailExists() {
                verify(adminRepository).existsByEmail(adminRequestDTO.getEmail());
            }

            @Test
            @DisplayName("Then should encode password")
            void thenShouldEncodePassword() {
                verify(passwordEncoder).encode(anyString());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(adminRepository).save(user);
            }
        }

        @Nested
        @DisplayName("When create is invalid - email already exists")
        class WhenCreateIsInvalidEmailExists {
            final AdminRequestDTO adminRequestDTO = AdminRequestMock.getAdminRequestDTO();

            @BeforeEach
            void setUp() {
                when(adminRepository.existsByEmail(adminRequestDTO.getEmail())).thenReturn(true);
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> adminService.create(adminRequestDTO));
            }
        }
    }

    @Nested
    @DisplayName("Given update is called")
    class GivenUpdateIsCalled {
        @Nested
        @DisplayName("When update is valid")
        class WhenUpdateIsValid {
            final Long id = 1L;
            final AdminUpdateDTO adminUpdateDTO = AdminUpdateMock.getAdminUpdateDTO();
            final AdminResponseDTO adminResponse = AdminResponseMock.getAdminResponseDTO();
            final User user = UserMock.getUser();
            private AdminResponseDTO result;

            @BeforeEach
            void setUp() {
                when(adminRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(adminRepository.existsByEmailAndNotId(adminUpdateDTO.getEmail(), id)).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(adminRepository.save(user)).thenReturn(user);
                when(adminMapper.toResponseDTO(user)).thenReturn(adminResponse);

                result = adminService.update(id, adminUpdateDTO);
            }

            @Test
            @DisplayName("Then should return updated admin")
            void thenShouldReturnUpdatedAdmin() {
                assertEquals(adminResponse, result);
            }

            @Test
            @DisplayName("Then should call repository to find user")
            void thenShouldCallRepositoryToFindUser() {
                verify(adminRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should check if email exists for other user")
            void thenShouldCheckIfEmailExistsForOtherUser() {
                verify(adminRepository).existsByEmailAndNotId(adminUpdateDTO.getEmail(), id);
            }

            @Test
            @DisplayName("Then should encode new password")
            void thenShouldEncodeNewPassword() {
                verify(passwordEncoder).encode(anyString());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(adminRepository).save(any(User.class));
            }
        }

        @Nested
        @DisplayName("When update is invalid - admin not found")
        class WhenUpdateIsInvalidAdminNotFound {
            final Long id = 999L;
            final AdminUpdateDTO adminUpdateDTO = new AdminUpdateDTO();

            @BeforeEach
            void setUp() {
                when(adminRepository.findEnabledById(id)).thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> adminService.update(id, adminUpdateDTO));
            }
        }

        @Nested
        @DisplayName("When update is invalid - email already in use")
        class WhenUpdateIsInvalidEmailInUse {
            final Long id = 1L;
            final AdminUpdateDTO adminUpdateDTO = AdminUpdateMock.getAdminUpdateDTO();
            final User user = UserMock.getUser();

            @BeforeEach
            void setUp() {
                when(adminRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(adminRepository.existsByEmailAndNotId(adminUpdateDTO.getEmail(), id)).thenReturn(true);
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> adminService.update(id, adminUpdateDTO));
            }
        }
    }

    @Nested
    @DisplayName("Given delete is called")
    class GivenDeleteIsCalled {
        @Nested
        @DisplayName("When delete is valid")
        class WhenDeleteIsValid {
            final Long id = 1L;
            final User user = UserMock.getUser();

            @BeforeEach
            void setUp() {
                when(adminRepository.findEnabledById(id)).thenReturn(Optional.of(user));

                adminService.delete(id);
            }

            @Test
            @DisplayName("Then should call repository to find user")
            void thenShouldCallRepositoryToFindUser() {
                verify(adminRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should set enabled to false")
            void thenShouldSetEnabledToFalse() {
                assertFalse(user.getEnabled());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(adminRepository).save(user);
            }
        }

        @Nested
        @DisplayName("When delete is invalid - admin not found")
        class WhenDeleteIsInvalidAdminNotFound {
            final Long id = 999L;

            @BeforeEach
            void setUp() {
                when(adminRepository.findEnabledById(id)).thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> adminService.delete(id));
            }
        }
    }

}
