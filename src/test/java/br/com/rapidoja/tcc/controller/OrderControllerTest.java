package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;
import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;
import br.com.rapidoja.tcc.dto.order.OrderUpdateDTO;
import br.com.rapidoja.tcc.mocks.order.OrderRequestMock;
import br.com.rapidoja.tcc.mocks.order.OrderResponseMock;
import br.com.rapidoja.tcc.mocks.order.OrderUpdateMock;
import br.com.rapidoja.tcc.service.OrderService;
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
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockitoBean
    private OrderService orderService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(orderService);
    }

    @Nested
    @DisplayName("Given get all orders")
    class GivenGetOrders {

        @Nested
        @DisplayName("When order list is called with success")
        class WhenOrderListIsCalledWithSuccess {
            private MvcResult result;
            private final List<OrderResponseDTO> orderResponseDTOList = OrderResponseMock.getOrderResponseList();

            @BeforeEach
            void setUp() throws Exception {
                when(orderService.findAll()).thenReturn(orderResponseDTOList);

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders").header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return order list with success")
            void thenShouldReturnOrderList() throws Exception {
                List<OrderResponseDTO> resultDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<OrderResponseDTO>>() {
                });
                assertEquals(orderResponseDTOList, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given get order by id")
    class GivenGetOrderById {
        @Nested
        @DisplayName("When order by id is called with success")
        class WhenOrderByIdIsCalledWithSuccess {
            private MvcResult result;
            private final OrderResponseDTO orderResponseDTO = OrderResponseMock.getOrderResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                final Long orderId = 1L;
                when(orderService.findById(orderId)).thenReturn(Optional.of(orderResponseDTO));

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/" + orderId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return order dto with success")
            void thenShouldReturnOrderDto() throws Exception {
                OrderResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), OrderResponseDTO.class);
                assertEquals(orderResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When order by id is called with error")
        class WhenOrderByIdIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final Long orderId = 1L;
                when(orderService.findById(orderId)).thenReturn(Optional.empty());

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/" + orderId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 404")
            void thenShouldReturn404() {
                assertEquals(404, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given create order")
    class GivenCreateOrder {
        @Nested
        @DisplayName("When order is created with success")
        class WhenOrderIsCreatedWithSuccess {
            private MvcResult result;
            private final OrderRequestDTO orderRequestDTO = OrderRequestMock.getOrderRequestDTO();
            private final OrderResponseDTO orderResponseDTO = OrderResponseMock.getOrderResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                when(orderService.create(orderRequestDTO)).thenReturn(orderResponseDTO);

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(orderRequestDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return order response with success")
            void thenShouldReturnOrderResponse() throws Exception {
                OrderResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), OrderResponseDTO.class);
                assertEquals(orderResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 201")
            void thenShouldReturn201() {
                assertEquals(201, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When order creation fails")
        class WhenOrderCreationFails {
            private MvcResult result;
            private final OrderRequestDTO orderRequestDTO = OrderRequestMock.getOrderRequestDTO();

            @BeforeEach
            void setUp() throws Exception {
                when(orderService.create(orderRequestDTO)).thenThrow(new IllegalArgumentException("Customer not found"));

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(orderRequestDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return 400")
            void thenShouldReturn400() {
                assertEquals(400, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given update order")
    class GivenUpdateOrder {
        @Nested
        @DisplayName("When order is updated with success")
        class WhenOrderIsUpdatedWithSuccess {
            private MvcResult result;
            private final OrderUpdateDTO orderUpdateDTO = OrderUpdateMock.getOrderUpdateDTO();
            private final OrderResponseDTO orderResponseDTO = OrderResponseMock.getOrderResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                final Long orderId = 1L;
                when(orderService.update(orderId, orderUpdateDTO)).thenReturn(orderResponseDTO);

                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/" + orderId)
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(orderUpdateDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return order response with success")
            void thenShouldReturnOrderResponse() throws Exception {
                OrderResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), OrderResponseDTO.class);
                assertEquals(orderResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When order update fails")
        class WhenOrderUpdateFails {
            private MvcResult result;
            private final OrderUpdateDTO orderUpdateDTO = OrderUpdateMock.getOrderUpdateDTO();

            @BeforeEach
            void setUp() throws Exception {
                final Long orderId = 1L;
                when(orderService.update(orderId, orderUpdateDTO)).thenThrow(new IllegalArgumentException("Order not found"));

                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/" + orderId)
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(orderUpdateDTO)))
                        .andReturn();
            }

            @Test
            @DisplayName("Then should return 400")
            void thenShouldReturn400() {
                assertEquals(400, result.getResponse().getStatus());
            }
        }
    }
}
