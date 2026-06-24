package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerUpdateDTO;
import br.com.rapidoja.tcc.mapper.CustomerMapper;
import br.com.rapidoja.tcc.mocks.UserMock;
import br.com.rapidoja.tcc.mocks.customer.CustomerRequestMock;
import br.com.rapidoja.tcc.mocks.customer.CustomerResponseMock;
import br.com.rapidoja.tcc.mocks.customer.CustomerUpdateMock;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.CustomerRepository;
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
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Nested
    @DisplayName("Given findAll is called")
    class GivenFindAllIsCalled {
        @Nested
        @DisplayName("When findAll is valid")
        class WhenFindAllIsValid {
            final CustomerResponseDTO customerResponse = CustomerResponseMock.getCustomerResponseDTO();
            final List<CustomerResponseDTO> expectedCustomers = List.of(customerResponse);
            final User user = UserMock.getUser();
            final List<User> usersList = List.of(user);
            private List<CustomerResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(customerRepository.findAllEnabled()).thenReturn(usersList);
                when(customerMapper.toResponseDTO(user)).thenReturn(customerResponse);

                result = customerService.findAll();
            }

            @Test
            @DisplayName("Then should return a list of customers")
            void thenShouldReturnAListOfCustomers() {
                assertEquals(expectedCustomers, result);
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(customerRepository).findAllEnabled();
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(customerMapper).toResponseDTO(user);
            }
        }
    }

    @Nested
    @DisplayName("Given findById is called")
    class GivenFindByIdIsCalled {
        @Nested
        @DisplayName("When findById is valid")
        class WhenFindByIdIsValid {
            final Long id = 1L;
            final CustomerResponseDTO customerResponse = CustomerResponseMock.getCustomerResponseDTO();
            final User user = UserMock.getUser();
            private Optional<CustomerResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(customerRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(customerMapper.toResponseDTO(user)).thenReturn(customerResponse);

                result = customerService.findById(id);
            }

            @Test
            @DisplayName("Then should return customer")
            void thenShouldReturnCustomer() {
                assertTrue(result.isPresent());
                assertEquals(customerResponse, result.get());
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(customerRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(customerMapper).toResponseDTO(user);
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
            final CustomerResponseDTO customerResponse = CustomerResponseMock.getCustomerResponseDTO();
            final User user = UserMock.getUser();
            private Optional<CustomerResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(customerRepository.findEnabledByEmail(email)).thenReturn(Optional.of(user));
                when(customerMapper.toResponseDTO(user)).thenReturn(customerResponse);

                result = customerService.findByEmail(email);
            }

            @Test
            @DisplayName("Then should return customer")
            void thenShouldReturnCustomer() {
                assertTrue(result.isPresent());
                assertEquals(customerResponse, result.get());
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(customerRepository).findEnabledByEmail(email);
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(customerMapper).toResponseDTO(user);
            }
        }
    }

    @Nested
    @DisplayName("Given create is called")
    class GivenCreateIsCalled {
        @Nested
        @DisplayName("When create is valid")
        class WhenCreateIsValid {
            final CustomerRequestDTO customerRequestDTO = CustomerRequestMock.getCustomerRequestDTO();
            final CustomerResponseDTO customerResponse = CustomerResponseMock.getCustomerResponseDTO();
            final User user = UserMock.getUser();
            private CustomerResponseDTO result;

            @BeforeEach
            void setUp() {

                when(customerRepository.existsByEmail(customerRequestDTO.getEmail())).thenReturn(false);
                when(customerMapper.toEntity(customerRequestDTO)).thenReturn(user);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(customerRepository.save(user)).thenReturn(user);
                when(customerMapper.toResponseDTO(user)).thenReturn(customerResponse);

                result = customerService.create(customerRequestDTO);
            }

            @Test
            @DisplayName("Then should return created customer")
            void thenShouldReturnCreatedCustomer() {
                assertEquals(customerResponse, result);
            }

            @Test
            @DisplayName("Then should check if email exists")
            void thenShouldCheckIfEmailExists() {
                verify(customerRepository).existsByEmail(customerRequestDTO.getEmail());
            }

            @Test
            @DisplayName("Then should encode password")
            void thenShouldEncodePassword() {
                verify(passwordEncoder).encode(anyString());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(customerRepository).save(user);
            }
        }

        @Nested
        @DisplayName("When create is invalid - email already exists")
        class WhenCreateIsInvalidEmailExists {
            final CustomerRequestDTO customerRequestDTO = CustomerRequestMock.getCustomerRequestDTO();

            @BeforeEach
            void setUp() {
                when(customerRepository.existsByEmail(customerRequestDTO.getEmail())).thenReturn(true);
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> customerService.create(customerRequestDTO));
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
            final CustomerUpdateDTO customerUpdateDTO = CustomerUpdateMock.getCustomerUpdateDTO();
            final CustomerResponseDTO customerResponse = CustomerResponseMock.getCustomerResponseDTO();
            final User user = UserMock.getUser();
            private CustomerResponseDTO result;

            @BeforeEach
            void setUp() {
                when(customerRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(customerRepository.existsByEmailAndNotId(customerUpdateDTO.getEmail(), id)).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(customerRepository.save(user)).thenReturn(user);
                when(customerMapper.toResponseDTO(user)).thenReturn(customerResponse);

                result = customerService.update(id, customerUpdateDTO);
            }

            @Test
            @DisplayName("Then should return updated customer")
            void thenShouldReturnUpdatedCustomer() {
                assertEquals(customerResponse, result);
            }

            @Test
            @DisplayName("Then should call repository to find user")
            void thenShouldCallRepositoryToFindUser() {
                verify(customerRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should check if email exists for other user")
            void thenShouldCheckIfEmailExistsForOtherUser() {
                verify(customerRepository).existsByEmailAndNotId(customerUpdateDTO.getEmail(), id);
            }

            @Test
            @DisplayName("Then should encode new password")
            void thenShouldEncodeNewPassword() {
                verify(passwordEncoder).encode(anyString());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(customerRepository).save(any(User.class));
            }
        }

        @Nested
        @DisplayName("When update is invalid - customer not found")
        class WhenUpdateIsInvalidCustomerNotFound {
            final Long id = 999L;
            final CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO();

            @BeforeEach
            void setUp() {
                when(customerRepository.findEnabledById(id)).thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> customerService.update(id, customerUpdateDTO));
            }
        }

        @Nested
        @DisplayName("When update is invalid - email already in use")
        class WhenUpdateIsInvalidEmailInUse {
            final Long id = 1L;
            final CustomerUpdateDTO customerUpdateDTO = CustomerUpdateMock.getCustomerUpdateDTO();
            final User user = UserMock.getUser();

            @BeforeEach
            void setUp() {
                when(customerRepository.findEnabledById(id)).thenReturn(Optional.of(user));
                when(customerRepository.existsByEmailAndNotId(customerUpdateDTO.getEmail(), id)).thenReturn(true);
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> customerService.update(id, customerUpdateDTO));
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
                when(customerRepository.findEnabledById(id)).thenReturn(Optional.of(user));

                customerService.delete(id);
            }

            @Test
            @DisplayName("Then should call repository to find user")
            void thenShouldCallRepositoryToFindUser() {
                verify(customerRepository).findEnabledById(id);
            }

            @Test
            @DisplayName("Then should set enabled to false")
            void thenShouldSetEnabledToFalse() {
                assertFalse(user.getEnabled());
            }

            @Test
            @DisplayName("Then should save user")
            void thenShouldSaveUser() {
                verify(customerRepository).save(user);
            }
        }

        @Nested
        @DisplayName("When delete is invalid - customer not found")
        class WhenDeleteIsInvalidCustomerNotFound {
            final Long id = 999L;

            @BeforeEach
            void setUp() {
                when(customerRepository.findEnabledById(id)).thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> customerService.delete(id));
            }
        }
    }

}