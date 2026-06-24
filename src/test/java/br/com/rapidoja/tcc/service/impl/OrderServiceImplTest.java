package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;
import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateDTO;
import br.com.rapidoja.tcc.mapper.OrderMapper;
import br.com.rapidoja.tcc.mocks.UserMock;
import br.com.rapidoja.tcc.mocks.order.OrderRequestMock;
import br.com.rapidoja.tcc.mocks.order.OrderResponseMock;
import br.com.rapidoja.tcc.mocks.order.OrderUpdateMock;
import br.com.rapidoja.tcc.model.Address;
import br.com.rapidoja.tcc.model.Order;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.DeliveryManRepository;
import br.com.rapidoja.tcc.repository.OrderRepository;
import br.com.rapidoja.tcc.repository.UserRepository;
import br.com.rapidoja.tcc.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeliveryManRepository deliveryManRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Nested
    @DisplayName("Given findAll is called")
    class GivenFindAllIsCalled {
        @Nested
        @DisplayName("When findAll is valid")
        class WhenFindAllIsValid {
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final List<OrderResponseDTO> expectedOrders = List.of(orderResponse);
            final Order order = new Order();
            final List<Order> ordersList = List.of(order);
            private List<OrderResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(orderRepository.findAll()).thenReturn(ordersList);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.findAll();
            }

            @Test
            @DisplayName("Then should return a list of orders")
            void thenShouldReturnAListOfOrders() {
                assertEquals(expectedOrders, result);
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(orderRepository).findAll();
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(orderMapper).toResponseDTO(order);
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
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            private Optional<OrderResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.findById(id);
            }

            @Test
            @DisplayName("Then should return order")
            void thenShouldReturnOrder() {
                assertTrue(result.isPresent());
                assertEquals(orderResponse, result.get());
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(orderRepository).findById(id);
            }

            @Test
            @DisplayName("Then should call mapper")
            void thenShouldCallMapper() {
                verify(orderMapper).toResponseDTO(order);
            }
        }
    }

    @Nested
    @DisplayName("Given findByCustomerId is called")
    class GivenFindByCustomerIdIsCalled {
        @Nested
        @DisplayName("When findByCustomerId is valid")
        class WhenFindByCustomerIdIsValid {
            final Long customerId = 1L;
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final List<Order> ordersList = List.of(order);
            private List<OrderResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(orderRepository.findByCustomerId(customerId)).thenReturn(ordersList);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.findByCustomerId(customerId);
            }

            @Test
            @DisplayName("Then should return list of orders")
            void thenShouldReturnListOfOrders() {
                assertEquals(List.of(orderResponse), result);
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(orderRepository).findByCustomerId(customerId);
            }
        }
    }

    @Nested
    @DisplayName("Given create is called")
    class GivenCreateIsCalled {
        @Nested
        @DisplayName("When create is valid")
        class WhenCreateIsValid {
            final OrderRequestDTO orderRequestDTO = OrderRequestMock.getOrderRequestDTO();
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final User customer = UserMock.getUser();
            final Address address = new Address();
            final Order order = new Order();
            private OrderResponseDTO result;

            @BeforeEach
            void setUp() {
                when(userRepository.findById(orderRequestDTO.getCustomerId())).thenReturn(Optional.of(customer));
                when(addressService.findOrCreate(
                        orderRequestDTO.getAddress()
                )).thenReturn(address);
                when(orderRepository.save(any(Order.class))).thenReturn(order);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.create(orderRequestDTO);
            }

            @Test
            @DisplayName("Then should return created order")
            void thenShouldReturnCreatedOrder() {
                assertEquals(orderResponse, result);
            }

            @Test
            @DisplayName("Then should call userRepository to find customer")
            void thenShouldCallUserRepository() {
                verify(userRepository).findById(orderRequestDTO.getCustomerId());
            }

            @Test
            @DisplayName("Then should call addressService to find or create address")
            void thenShouldCallAddressService() {
                verify(addressService).findOrCreate(
                        orderRequestDTO.getAddress()
                );
            }

            @Test
            @DisplayName("Then should call orderRepository to save")
            void thenShouldCallOrderRepository() {
                verify(orderRepository).save(any(Order.class));
            }
        }

        @Nested
        @DisplayName("When customer not found")
        class WhenCustomerNotFound {
            final OrderRequestDTO orderRequestDTO = OrderRequestMock.getOrderRequestDTO();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(userRepository.findById(orderRequestDTO.getCustomerId())).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.create(orderRequestDTO));
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
            final OrderUpdateDTO orderUpdateDTO = OrderUpdateMock.getOrderUpdateDTO();
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final User deliveryMan = UserMock.getUser();
            private OrderResponseDTO result;

            @BeforeEach
            void setUp() {
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledById(orderUpdateDTO.getDeliveryManId())).thenReturn(Optional.of(deliveryMan));
                when(orderRepository.save(order)).thenReturn(order);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.update(id, orderUpdateDTO);
            }

            @Test
            @DisplayName("Then should return updated order")
            void thenShouldReturnUpdatedOrder() {
                assertEquals(orderResponse, result);
            }

            @Test
            @DisplayName("Then should call repository to find")
            void thenShouldCallRepositoryToFind() {
                verify(orderRepository).findById(id);
            }

            @Test
            @DisplayName("Then should call repository to save")
            void thenShouldCallRepositoryToSave() {
                verify(orderRepository).save(order);
            }
        }

        @Nested
        @DisplayName("When order not found")
        class WhenOrderNotFound {
            final Long id = 1L;
            final OrderUpdateDTO orderUpdateDTO = OrderUpdateMock.getOrderUpdateDTO();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(orderRepository.findById(id)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.update(id, orderUpdateDTO));
            }
        }
    }
}
