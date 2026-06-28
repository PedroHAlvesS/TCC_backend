package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;
import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateAssignDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateStatusDTO;
import br.com.rapidoja.tcc.mapper.OrderMapper;
import br.com.rapidoja.tcc.mocks.UserMock;
import br.com.rapidoja.tcc.mocks.order.OrderRequestMock;
import br.com.rapidoja.tcc.mocks.order.OrderResponseMock;
import br.com.rapidoja.tcc.mocks.order.OrderUpdateAssignMock;
import br.com.rapidoja.tcc.mocks.order.OrderUpdateStatusMock;
import br.com.rapidoja.tcc.model.Address;
import br.com.rapidoja.tcc.model.Order;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.repository.CustomerRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private CustomerRepository customerRepository;

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
    @DisplayName("Given findByDeliveryManId is called")
    class GivenFindByDeliveryManIdIsCalled {
        @Nested
        @DisplayName("When findByDeliveryManId is valid")
        class WhenFindByDeliveryManIdIsValid {
            final Long deliveryId = 1L;
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final List<Order> ordersList = List.of(order);
            private List<OrderResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(orderRepository.findByDeliveryManId(deliveryId)).thenReturn(ordersList);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.findByDeliveryManId(deliveryId);
            }

            @Test
            @DisplayName("Then should return list of orders")
            void thenShouldReturnListOfOrders() {
                assertEquals(List.of(orderResponse), result);
            }

            @Test
            @DisplayName("Then should call repository")
            void thenShouldCallRepository() {
                verify(orderRepository).findByDeliveryManId(deliveryId);
            }
        }
    }

    @Nested
    @DisplayName("Given create is called")
    class GivenCreateIsCalled {
        final String email = "email@test.com";
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
                when(customerRepository.findEnabledByEmail(email)).thenReturn(Optional.of(customer));
                when(addressService.findOrCreate(
                        orderRequestDTO.getAddress()
                )).thenReturn(address);
                when(orderRepository.save(any(Order.class))).thenReturn(order);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.create(orderRequestDTO, email);
            }

            @Test
            @DisplayName("Then should return created order")
            void thenShouldReturnCreatedOrder() {
                assertEquals(orderResponse, result);
            }

            @Test
            @DisplayName("Then should call userRepository to find customer")
            void thenShouldCallUserRepository() {
                verify(customerRepository).findEnabledByEmail(email);
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
                when(customerRepository.findEnabledByEmail(email)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.create(orderRequestDTO, email));
            }
        }
    }

    @Nested
    @DisplayName("Given updateAssign is called")
    class GivenUpdateAssignIsCalled {
        @Nested
        @DisplayName("When updateAssign is valid")
        class WhenUpdateAssignIsValid {
            final Long id = 1L;
            final OrderUpdateAssignDTO orderUpdateAssignDTO = OrderUpdateAssignMock.getOrderUpdateAssignDTO();
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final User deliveryMan = UserMock.getUser();
            private OrderResponseDTO result;

            @BeforeEach
            void setUp() {
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledById(orderUpdateAssignDTO.getDeliveryManId())).thenReturn(Optional.of(deliveryMan));
                when(orderRepository.save(order)).thenReturn(order);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.updateAssign(id, orderUpdateAssignDTO);
            }

            @Test
            @DisplayName("Then should return updated order")
            void thenShouldReturnUpdatedOrder() {
                assertEquals(orderResponse, result);
            }

            @Test
            @DisplayName("Then should call repository to find order")
            void thenShouldCallRepositoryToFindOrder() {
                verify(orderRepository).findById(id);
            }

            @Test
            @DisplayName("Then should call repository to find delivery man")
            void thenShouldCallRepositoryToFindDeliveryMan() {
                verify(deliveryManRepository).findEnabledById(orderUpdateAssignDTO.getDeliveryManId());
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
            final OrderUpdateAssignDTO orderUpdateAssignDTO = OrderUpdateAssignMock.getOrderUpdateAssignDTO();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(orderRepository.findById(id)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.updateAssign(id, orderUpdateAssignDTO));
            }
        }

        @Nested
        @DisplayName("When delivery man not found")
        class WhenDeliveryManNotFound {
            final Long id = 1L;
            final OrderUpdateAssignDTO orderUpdateAssignDTO = OrderUpdateAssignMock.getOrderUpdateAssignDTO();
            final Order order = new Order();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledById(orderUpdateAssignDTO.getDeliveryManId())).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.updateAssign(id, orderUpdateAssignDTO));
            }
        }
    }

    @Nested
    @DisplayName("Given findByCustomerEmail is called")
    class GivenFindByCustomerEmailIsCalled {
        @Nested
        @DisplayName("When findByCustomerEmail is valid")
        class WhenFindByCustomerEmailIsValid {
            final String email = "customer@test.com";
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final List<Order> ordersList = List.of(order);
            final User user = UserMock.getUser();
            private List<OrderResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
                when(orderRepository.findByCustomerId(user.getId())).thenReturn(ordersList);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.findByCustomerEmail(email);
            }

            @Test
            @DisplayName("Then should return list of orders")
            void thenShouldReturnListOfOrders() {
                assertEquals(List.of(orderResponse), result);
            }

            @Test
            @DisplayName("Then should call userRepository to find user")
            void thenShouldCallUserRepository() {
                verify(userRepository).findByEmail(email);
            }

            @Test
            @DisplayName("Then should call orderRepository to find orders")
            void thenShouldCallOrderRepository() {
                verify(orderRepository).findByCustomerId(user.getId());
            }
        }

        @Nested
        @DisplayName("When customer not found")
        class WhenCustomerNotFound {
            final String email = "customer@test.com";

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.findByCustomerEmail(email));
            }
        }
    }

    @Nested
    @DisplayName("Given findByDeliveryManEmail is called")
    class GivenFindByDeliveryManEmailIsCalled {
        @Nested
        @DisplayName("When findByDeliveryManEmail is valid")
        class WhenFindByDeliveryManEmailIsValid {
            final String email = "delivery@test.com";
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final List<Order> ordersList = List.of(order);
            final User user = UserMock.getUser();
            private List<OrderResponseDTO> result;

            @BeforeEach
            void setUp() {
                when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
                when(orderRepository.findByDeliveryManId(user.getId())).thenReturn(ordersList);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.findByDeliveryManEmail(email);
            }

            @Test
            @DisplayName("Then should return list of orders")
            void thenShouldReturnListOfOrders() {
                assertEquals(List.of(orderResponse), result);
            }

            @Test
            @DisplayName("Then should call userRepository to find user")
            void thenShouldCallUserRepository() {
                verify(userRepository).findByEmail(email);
            }

            @Test
            @DisplayName("Then should call orderRepository to find orders")
            void thenShouldCallOrderRepository() {
                verify(orderRepository).findByDeliveryManId(user.getId());
            }
        }

        @Nested
        @DisplayName("When delivery man not found")
        class WhenDeliveryManNotFound {
            final String email = "delivery@test.com";

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.findByDeliveryManEmail(email));
            }
        }
    }

    @Nested
    @DisplayName("Given updateStatusByDeliveryMan is called")
    class GivenUpdateStatusByDeliveryManIsCalled {
        final String email = "delivery@test.com";
        @Nested
        @DisplayName("When updateStatusByDeliveryMan is valid")
        class WhenUpdateStatusByDeliveryManIsValid {
            final Long id = 1L;
            final OrderUpdateStatusDTO orderUpdateStatusDTO = OrderUpdateStatusMock.getOrderUpdateStatusDTO();
            final OrderResponseDTO orderResponse = OrderResponseMock.getOrderResponseDTO();
            final Order order = new Order();
            final User deliveryMan = UserMock.getUser();
            private OrderResponseDTO result;

            @BeforeEach
            void setUp() {
                order.setDeliveryMan(deliveryMan);
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledByEmail(email)).thenReturn(Optional.of(deliveryMan));
                when(orderRepository.save(order)).thenReturn(order);
                when(orderMapper.toResponseDTO(order)).thenReturn(orderResponse);

                result = orderService.updateStatusByDeliveryMan(id, orderUpdateStatusDTO, email);
            }

            @Test
            @DisplayName("Then should return updated order")
            void thenShouldReturnUpdatedOrder() {
                assertEquals(orderResponse, result);
            }

            @Test
            @DisplayName("Then should call repository to find order")
            void thenShouldCallRepositoryToFindOrder() {
                verify(orderRepository).findById(id);
            }

            @Test
            @DisplayName("Then should call repository to find delivery man")
            void thenShouldCallRepositoryToFindDeliveryMan() {
                verify(deliveryManRepository).findEnabledByEmail(email);
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
            final OrderUpdateStatusDTO orderUpdateStatusDTO = OrderUpdateStatusMock.getOrderUpdateStatusDTO();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(orderRepository.findById(id)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.updateStatusByDeliveryMan(id, orderUpdateStatusDTO, email));
            }
        }

        @Nested
        @DisplayName("When delivery man not found")
        class WhenDeliveryManNotFound {
            final Long id = 1L;
            final OrderUpdateStatusDTO orderUpdateStatusDTO = OrderUpdateStatusMock.getOrderUpdateStatusDTO();
            final Order order = new Order();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledByEmail(email)).thenReturn(Optional.empty());

                assertThrows(IllegalArgumentException.class, () -> orderService.updateStatusByDeliveryMan(id, orderUpdateStatusDTO, email));
            }
        }

        @Nested
        @DisplayName("When delivery man not authorized")
        class WhenDeliveryManNotAuthorized {
            final Long id = 2L;
            final OrderUpdateStatusDTO orderUpdateStatusDTO = OrderUpdateStatusMock.getOrderUpdateStatusDTO();
            final Order order = new Order();
            final User deliveryMan = UserMock.getUserWithId(999L);
            final User otherDeliveryMan = UserMock.getUser();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                order.setDeliveryMan(otherDeliveryMan);
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledByEmail(email)).thenReturn(Optional.of(deliveryMan));

                assertThrows(IllegalArgumentException.class, () -> orderService.updateStatusByDeliveryMan(id, orderUpdateStatusDTO, email));
            }
        }

        @Nested
        @DisplayName("When status is invalid")
        class WhenStatusIsInvalid {
            final Long id = 1L;
            final OrderUpdateStatusDTO orderUpdateStatusDTO = new OrderUpdateStatusDTO("INVALID_STATUS");
            final Order order = new Order();
            final User deliveryMan = UserMock.getUser();

            @Test
            @DisplayName("Then should throw IllegalArgumentException")
            void thenShouldThrowException() {
                order.setDeliveryMan(deliveryMan);
                when(orderRepository.findById(id)).thenReturn(Optional.of(order));
                when(deliveryManRepository.findEnabledByEmail(email)).thenReturn(Optional.of(deliveryMan));

                assertThrows(IllegalArgumentException.class, () -> orderService.updateStatusByDeliveryMan(id, orderUpdateStatusDTO, email));
            }
        }
    }

}
