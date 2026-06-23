package br.com.rapidoja.tcc.controller;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerUpdateDTO;
import br.com.rapidoja.tcc.mocks.customer.CustomerRequestMock;
import br.com.rapidoja.tcc.mocks.customer.CustomerResponseMock;
import br.com.rapidoja.tcc.mocks.customer.CustomerUpdateMock;
import br.com.rapidoja.tcc.service.CustomerService;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper =  new ObjectMapper();

    CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
    }

    @Nested
    @DisplayName("Given get all customers")
    class GivenGetCustomers {

        @Nested
        @DisplayName("When customer list is called with success")
        class WhenCustomerListIsCalledWithSuccess {
            private MvcResult result;
            private final List<CustomerResponseDTO> customerResponseDTOList = CustomerResponseMock.getCustomerResponseList();

            @BeforeEach
            void setUp() throws Exception {
                when(customerService.findAll()).thenReturn(customerResponseDTOList);

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers").header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return customer list with success")
            void thenShouldReturnCustomerList() throws Exception {
                List<CustomerResponseDTO> resultDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CustomerResponseDTO>>() {
                });
                assertEquals(customerResponseDTOList, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

    }

    @Nested
    @DisplayName("Given get customer by id")
    class GivenGetCustomerById {
        @Nested
        @DisplayName("When customer by id is called with success")
        class WhenCustomerByIdIsCalledWithSuccess {
            private MvcResult result;
            private final CustomerResponseDTO customerResponseDTO = CustomerResponseMock.getCustomerResponseDTO();


            @BeforeEach
            void setUp() throws Exception {
                final Long customerId = 1L;
                when(customerService.findById(customerId)).thenReturn(Optional.of(customerResponseDTO));

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/"+ customerId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return customer dto with success")
            void thenShouldReturnCustomerDto() throws Exception {
                CustomerResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), CustomerResponseDTO.class);
                assertEquals(customerResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When customer by id is called with error")
        class WhenCustomerByIdIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final Long customerId = 1L;
                when(customerService.findById(customerId)).thenReturn(Optional.empty());

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/"+ customerId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 404")
            void thenShouldReturn404() {
                assertEquals(404, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given get customer by email")
    class GivenGetCustomerByEmail {
        @Nested
        @DisplayName("When customer by email is called with success")
        class WhenCustomerByEmailIsCalledWithSuccess {
            private MvcResult result;
            private final CustomerResponseDTO customerResponseDTO = CustomerResponseMock.getCustomerResponseDTO();


            @BeforeEach
            void setUp() throws Exception {
                final String customerEmail = "teste@email.com";
                when(customerService.findByEmail(customerEmail)).thenReturn(Optional.of(customerResponseDTO));

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/email/"+ customerEmail).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return customer dto with success")
            void thenShouldReturnCustomerDto() throws Exception {
                CustomerResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), CustomerResponseDTO.class);
                assertEquals(customerResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When customer by id is called with error")
        class WhenCustomerByIdIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final String customerEmail = "teste@email.com";
                when(customerService.findByEmail(customerEmail)).thenReturn(Optional.empty());

                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/email/"+ customerEmail).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 404")
            void thenShouldReturn404() {
                assertEquals(404, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given create customer")
    class GivenCreateCustomer {
        @Nested
        @DisplayName("When create customer called with success")
        class WhenCustomerByEmailIsCalledWithSuccess {
            private MvcResult result;
            private final CustomerResponseDTO customerResponseDTO = CustomerResponseMock.getCustomerResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                final CustomerRequestDTO customerRequestDTO = CustomerRequestMock.getCustomerRequestDTO();
                when(customerService.create(customerRequestDTO)).thenReturn(customerResponseDTO);

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customers").header("Content-Type", "application/json").content(mapper.writeValueAsString(customerRequestDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return customer dto with success")
            void thenShouldReturnCustomerDto() throws Exception {
                CustomerResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), CustomerResponseDTO.class);
                assertEquals(customerResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 201")
            void thenShouldReturn201() {
                assertEquals(201, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When create customer is called with error")
        class WhenCreateCustomerIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final CustomerRequestDTO customerRequestDTO = CustomerRequestMock.getCustomerRequestDTO();
                when(customerService.create(customerRequestDTO)).thenThrow(new IllegalArgumentException());

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customers").header("Content-Type", "application/json").content(mapper.writeValueAsString(customerRequestDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return 400")
            void thenShouldReturn400() {
                assertEquals(400, result.getResponse().getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Given update customer")
    class GivenUpdateCustomer {
        @Nested
        @DisplayName("When update customer called with success")
        class WhenUpdateCustomerIsCalledWithSuccess {
            private MvcResult result;
            private final CustomerResponseDTO customerResponseDTO = CustomerResponseMock.getCustomerResponseDTO();

            @BeforeEach
            void setUp() throws Exception {
                final CustomerUpdateDTO customerUpdateDTO = CustomerUpdateMock.getCustomerUpdateDTO();
                final Long customerId = 1L;
                when(customerService.update(customerId, customerUpdateDTO)).thenReturn(customerResponseDTO);

                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/" + customerId).header("Content-Type", "application/json").content(mapper.writeValueAsString(customerUpdateDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return customer dto with success")
            void thenShouldReturnCustomerDto() throws Exception {
                CustomerResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), CustomerResponseDTO.class);
                assertEquals(customerResponseDTO, resultDto);
            }

            @Test
            @DisplayName("Then should return 200")
            void thenShouldReturn200() {
                assertEquals(200, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When update customer is called with error")
        class WhenUpdateCustomerIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final CustomerUpdateDTO customerUpdateDTO = CustomerUpdateMock.getCustomerUpdateDTO();
                final Long customerId = 1L;
                when(customerService.update(customerId, customerUpdateDTO)).thenThrow(new IllegalArgumentException());

                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/" + customerId).header("Content-Type", "application/json").content(mapper.writeValueAsString(customerUpdateDTO))).andReturn();
            }

            @Test
            @DisplayName("Then should return 400")
            void thenShouldReturn400() {
                assertEquals(400, result.getResponse().getStatus());
            }
        }

    }

    @Nested
    @DisplayName("Given delete customer")
    class GivenDeleteCustomer {
        @Nested
        @DisplayName("When delete customer called with success")
        class WhenDeleteCustomerIsCalledWithSuccess {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final Long customerId = 1L;
                doNothing().when(customerService).delete(customerId);

                result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + customerId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 204")
            void thenShouldReturn204() {
                assertEquals(204, result.getResponse().getStatus());
            }
        }

        @Nested
        @DisplayName("When delete customer is called with error")
        class WhenDeleteCustomerIsCalledWithError {
            private MvcResult result;

            @BeforeEach
            void setUp() throws Exception {
                final Long customerId = 1L;

                doThrow(new IllegalArgumentException("Customer not found"))
                        .when(customerService)
                        .delete(customerId);

                result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + customerId).header("Content-Type", "application/json")).andReturn();
            }

            @Test
            @DisplayName("Then should return 404")
            void thenShouldReturn404() {
                assertEquals(404, result.getResponse().getStatus());
            }
        }
    }
}