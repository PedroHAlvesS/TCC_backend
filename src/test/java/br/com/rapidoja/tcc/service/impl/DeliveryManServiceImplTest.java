package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;
import br.com.rapidoja.tcc.mapper.DeliveryManMapper;
import br.com.rapidoja.tcc.mocks.UserMock;
import br.com.rapidoja.tcc.mocks.deliveryman.DeliveryManRequestMock;
import br.com.rapidoja.tcc.mocks.deliveryman.DeliveryManResponseMock;
import br.com.rapidoja.tcc.mocks.deliveryman.DeliveryManUpdateMock;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.DeliveryManRepository;
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
class DeliveryManServiceImplTest {

    @Mock
    private DeliveryManRepository deliveryManRepository;

    @Mock
    private DeliveryManMapper deliveryManMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DeliveryManServiceImpl deliveryManService;

    @Nested
    @DisplayName("Given findAll is called")
    class GivenFindAllIsCalled {
        @Nested
        @DisplayName("When findAll is valid")
        class WhenFindAllIsValid {
            final DeliveryManResponseDTO deliveryManResponse = DeliveryManResponseMock.getDeliveryManResponseDTO();
            final List<DeliveryManResponseDTO> expectedDeliveryMen = List.of(deliveryManResponse);
            final User user = UserMock.getUser();
            final List<User> usersList = List.of(user);
            private List<DeliveryManResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.findAllEnabled()).thenReturn(usersList);
                when(deliveryManMapper.toResponseDTO(user)).thenReturn(deliveryManResponse);

                result = deliveryManService.findAll();
            }

            @Test
            @DisplayName("Then should return a list of delivery men")
            void thenShouldReturnAListOfDeliveryMen() {
                assertEquals(expectedDeliveryMen, result);
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(deliveryManRepository).findAllEnabled();
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(deliveryManMapper).toResponseDTO(user);
            }
        }
    }

    @Nested
    @DisplayName("Given findByEmail is called")
    class GivenFindByEmailIsCalled {
        @Nested
        @DisplayName("When findByEmail is valid")
        class WhenFindByEmailIsValid {
            final String email = "test@email.com";
            final DeliveryManResponseDTO deliveryManResponse = DeliveryManResponseMock.getDeliveryManResponseDTO();
            final User user = UserMock.getUser();
            private DeliveryManResponseDTO result;

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.findEnabledByEmail(email)).thenReturn(Optional.of(user));
                when(deliveryManMapper.toResponseDTO(user)).thenReturn(deliveryManResponse);

                result = deliveryManService.findByEmail(email);
            }

            @Test
            @DisplayName("Then should return delivery man")
            void thenShouldReturnDeliveryMan() {
                assertEquals(deliveryManResponse, result);
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(deliveryManRepository).findEnabledByEmail(email);
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(deliveryManMapper).toResponseDTO(user);
            }
        }
    }

    @Nested
    @DisplayName("Given create is called")
    class GivenCreateIsCalled {
        @Nested
        @DisplayName("When create is valid")
        class WhenCreateIsValid {
            final DeliveryManRequestDTO deliveryManRequestDTO = DeliveryManRequestMock.getDeliveryManRequestDTO();
            final DeliveryManResponseDTO deliveryManResponse = DeliveryManResponseMock.getDeliveryManResponseDTO();
            final User user = UserMock.getUser();
            private DeliveryManResponseDTO result;

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.existsByEmail(deliveryManRequestDTO.getEmail())).thenReturn(false);
                when(deliveryManMapper.toEntity(deliveryManRequestDTO)).thenReturn(user);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(deliveryManRepository.save(user)).thenReturn(user);
                when(deliveryManMapper.toResponseDTO(user)).thenReturn(deliveryManResponse);

                result = deliveryManService.create(deliveryManRequestDTO);
            }

            @Test
            @DisplayName("Then should return created delivery man")
            void thenShouldReturnCreatedDeliveryMan() {
                assertEquals(deliveryManResponse, result);
            }

            @Test
            @DisplayName("Then should check if email exists")
            void thenShouldCheckIfEmailExists() {
                verify(deliveryManRepository).existsByEmail(deliveryManRequestDTO.getEmail());
            }

            @Test
            @DisplayName("Then should encode password")
            void thenShouldEncodePassword() {
                verify(passwordEncoder).encode(anyString());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(deliveryManRepository).save(user);
            }
        }

        @Nested
        @DisplayName("When create is invalid - email already exists")
        class WhenCreateIsInvalidEmailExists {
            final DeliveryManRequestDTO deliveryManRequestDTO = DeliveryManRequestMock.getDeliveryManRequestDTO();

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.existsByEmail(deliveryManRequestDTO.getEmail())).thenReturn(true);
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> deliveryManService.create(deliveryManRequestDTO));
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
            final DeliveryManUpdateDTO deliveryManUpdateDTO = DeliveryManUpdateMock.getDeliveryManUpdateDTO();
            final DeliveryManResponseDTO deliveryManResponse = DeliveryManResponseMock.getDeliveryManResponseDTO();
            final User user = UserMock.getUser();
            private DeliveryManResponseDTO result;

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(deliveryManRepository.existsByEmailAndNotId(deliveryManUpdateDTO.getEmail(), id)).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(deliveryManRepository.save(user)).thenReturn(user);
                when(deliveryManMapper.toResponseDTO(user)).thenReturn(deliveryManResponse);

                result = deliveryManService.update(id, deliveryManUpdateDTO);
            }

            @Test
            @DisplayName("Then should return updated delivery man")
            void thenShouldReturnUpdatedDeliveryMan() {
                assertEquals(deliveryManResponse, result);
            }

            @Test
            @DisplayName("Then should call repository to find user")
            void thenShouldCallRepositoryToFindUser() {
                verify(deliveryManRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should check if email exists for other user")
            void thenShouldCheckIfEmailExistsForOtherUser() {
                verify(deliveryManRepository).existsByEmailAndNotId(deliveryManUpdateDTO.getEmail(), id);
            }

            @Test
            @DisplayName("Then should encode new password")
            void thenShouldEncodeNewPassword() {
                verify(passwordEncoder).encode(anyString());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(deliveryManRepository).save(any(User.class));
            }
        }

        @Nested
        @DisplayName("When update is invalid - delivery man not found")
        class WhenUpdateIsInvalidDeliveryManNotFound {
            final Long id = 999L;
            final DeliveryManUpdateDTO deliveryManUpdateDTO = new DeliveryManUpdateDTO();

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.findEnabledById(id)).thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> deliveryManService.update(id, deliveryManUpdateDTO));
            }
        }

        @Nested
        @DisplayName("When update is invalid - email already in use")
        class WhenUpdateIsInvalidEmailInUse {
            final Long id = 1L;
            final DeliveryManUpdateDTO deliveryManUpdateDTO = DeliveryManUpdateMock.getDeliveryManUpdateDTO();
            final User user = UserMock.getUser();

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(deliveryManRepository.existsByEmailAndNotId(deliveryManUpdateDTO.getEmail(), id)).thenReturn(true);
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> deliveryManService.update(id, deliveryManUpdateDTO));
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
                when(deliveryManRepository.findEnabledById(id)).thenReturn(Optional.of(user));

                deliveryManService.delete(id);
            }

            @Test
            @DisplayName("Then should call repository to find user")
            void thenShouldCallRepositoryToFindUser() {
                verify(deliveryManRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should set enabled to false")
            void thenShouldSetEnabledToFalse() {
                assertFalse(user.getEnabled());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(deliveryManRepository).save(user);
            }
        }

        @Nested
        @DisplayName("When delete is invalid - delivery man not found")
        class WhenDeleteIsInvalidDeliveryManNotFound {
            final Long id = 999L;

            @BeforeEach
            void setUp() {
                when(deliveryManRepository.findEnabledById(id)).thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> deliveryManService.delete(id));
            }
        }
    }

}
