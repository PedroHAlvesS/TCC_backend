//package br.com.rapidoja.tcc.controller;
//
//import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
//import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
//import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;
//import br.com.rapidoja.tcc.mocks.deliveryman.DeliveryManRequestMock;
//import br.com.rapidoja.tcc.mocks.deliveryman.DeliveryManResponseMock;
//import br.com.rapidoja.tcc.mocks.deliveryman.DeliveryManUpdateMock;
//import br.com.rapidoja.tcc.service.DeliveryManService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import tools.jackson.core.type.TypeReference;
//import tools.jackson.databind.ObjectMapper;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@WebMvcTest(DeliveryManController.class)
//class DeliveryManControllerTest {
//
//    @MockitoBean
//    private DeliveryManService deliveryManService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    ObjectMapper mapper =  new ObjectMapper();
//
//    DeliveryManController deliveryManController;
//
//    @BeforeEach
//    void setUp() {
//        deliveryManController = new DeliveryManController(deliveryManService);
//    }
//
//    @Nested
//    @DisplayName("Given get all delivery men")
//    class GivenGetDeliveryMen {
//
//        @Nested
//        @DisplayName("When delivery men list is called with success")
//        class WhenDeliveryMenListIsCalledWithSuccess {
//            private MvcResult result;
//            private final List<DeliveryManResponseDTO> deliveryManResponseDTOList = DeliveryManResponseMock.getDeliveryManResponseList();
//
//            @BeforeEach
//            void setUp() throws Exception {
//                when(deliveryManService.findAll()).thenReturn(deliveryManResponseDTOList);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery-men").header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return delivery men list with success")
//            void thenShouldReturnDeliveryMenList() throws Exception {
//                List<DeliveryManResponseDTO> resultDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DeliveryManResponseDTO>>() {
//                });
//                assertEquals(deliveryManResponseDTOList, resultDto);
//            }
//
//            @Test
//            @DisplayName("Then should return 200")
//            void thenShouldReturn200() {
//                assertEquals(200, result.getResponse().getStatus());
//            }
//        }
//
//    }
//
//    @Nested
//    @DisplayName("Given get delivery man by id")
//    class GivenGetDeliveryManById {
//        @Nested
//        @DisplayName("When delivery man by id is called with success")
//        class WhenDeliveryManByIdIsCalledWithSuccess {
//            private MvcResult result;
//            private final DeliveryManResponseDTO deliveryManResponseDTO = DeliveryManResponseMock.getDeliveryManResponseDTO();
//
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final Long deliveryManId = 1L;
//                when(deliveryManService.findById(deliveryManId)).thenReturn(Optional.of(deliveryManResponseDTO));
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery-men/"+ deliveryManId).header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return delivery man dto with success")
//            void thenShouldReturnDeliveryManDto() throws Exception {
//                DeliveryManResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), DeliveryManResponseDTO.class);
//                assertEquals(deliveryManResponseDTO, resultDto);
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
//        @DisplayName("When delivery man by id is called with error")
//        class WhenDeliveryManByIdIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final Long deliveryManId = 1L;
//                when(deliveryManService.findById(deliveryManId)).thenReturn(Optional.empty());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery-men/"+ deliveryManId).header("Content-Type", "application/json")).andReturn();
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
//    @DisplayName("Given get delivery man by email")
//    class GivenGetDeliveryManByEmail {
//        @Nested
//        @DisplayName("When delivery man by email is called with success")
//        class WhenDeliveryManByEmailIsCalledWithSuccess {
//            private MvcResult result;
//            private final DeliveryManResponseDTO deliveryManResponseDTO = DeliveryManResponseMock.getDeliveryManResponseDTO();
//
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final String deliveryManEmail = "teste@email.com";
//                when(deliveryManService.findByEmail(deliveryManEmail)).thenReturn(Optional.of(deliveryManResponseDTO));
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery-men/email/"+ deliveryManEmail).header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return delivery man dto with success")
//            void thenShouldReturnDeliveryManDto() throws Exception {
//                DeliveryManResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), DeliveryManResponseDTO.class);
//                assertEquals(deliveryManResponseDTO, resultDto);
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
//        @DisplayName("When delivery man by id is called with error")
//        class WhenDeliveryManByIdIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final String deliveryManEmail = "teste@email.com";
//                when(deliveryManService.findByEmail(deliveryManEmail)).thenReturn(Optional.empty());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery-men/email/"+ deliveryManEmail).header("Content-Type", "application/json")).andReturn();
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
//    @DisplayName("Given create delivery man")
//    class GivenCreateDeliveryMan {
//        @Nested
//        @DisplayName("When create delivery man called with success")
//        class WhenDeliveryManByEmailIsCalledWithSuccess {
//            private MvcResult result;
//            private final DeliveryManResponseDTO deliveryManResponseDTO = DeliveryManResponseMock.getDeliveryManResponseDTO();
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final DeliveryManRequestDTO deliveryManRequestDTO = DeliveryManRequestMock.getDeliveryManRequestDTO();
//                when(deliveryManService.create(deliveryManRequestDTO)).thenReturn(deliveryManResponseDTO);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/delivery-men").header("Content-Type", "application/json").content(mapper.writeValueAsString(deliveryManRequestDTO))).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return delivery man dto with success")
//            void thenShouldReturnDeliveryManDto() throws Exception {
//                DeliveryManResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), DeliveryManResponseDTO.class);
//                assertEquals(deliveryManResponseDTO, resultDto);
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
//        @DisplayName("When create delivery man is called with error")
//        class WhenCreateDeliveryManIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final DeliveryManRequestDTO deliveryManRequestDTO = DeliveryManRequestMock.getDeliveryManRequestDTO();
//                when(deliveryManService.create(deliveryManRequestDTO)).thenThrow(new IllegalArgumentException());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/delivery-men").header("Content-Type", "application/json").content(mapper.writeValueAsString(deliveryManRequestDTO))).andReturn();
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
//    @DisplayName("Given update delivery man")
//    class GivenUpdateDeliveryMan {
//        @Nested
//        @DisplayName("When update delivery man called with success")
//        class WhenUpdateDeliveryManIsCalledWithSuccess {
//            private MvcResult result;
//            private final DeliveryManResponseDTO deliveryManResponseDTO = DeliveryManResponseMock.getDeliveryManResponseDTO();
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final DeliveryManUpdateDTO deliveryManUpdateDTO = DeliveryManUpdateMock.getDeliveryManUpdateDTO();
//                final Long deliveryManId = 1L;
//                when(deliveryManService.update(deliveryManId, deliveryManUpdateDTO)).thenReturn(deliveryManResponseDTO);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery-men/" + deliveryManId).header("Content-Type", "application/json").content(mapper.writeValueAsString(deliveryManUpdateDTO))).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return delivery man dto with success")
//            void thenShouldReturnDeliveryManDto() throws Exception {
//                DeliveryManResponseDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), DeliveryManResponseDTO.class);
//                assertEquals(deliveryManResponseDTO, resultDto);
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
//        @DisplayName("When update delivery man is called with error")
//        class WhenUpdateDeliveryManIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final DeliveryManUpdateDTO deliveryManUpdateDTO = DeliveryManUpdateMock.getDeliveryManUpdateDTO();
//                final Long deliveryManId = 1L;
//                when(deliveryManService.update(deliveryManId, deliveryManUpdateDTO)).thenThrow(new IllegalArgumentException());
//
//                result = mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery-men/" + deliveryManId).header("Content-Type", "application/json").content(mapper.writeValueAsString(deliveryManUpdateDTO))).andReturn();
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
//
//    @Nested
//    @DisplayName("Given delete delivery man")
//    class GivenDeleteDeliveryMan {
//        @Nested
//        @DisplayName("When delete delivery man called with success")
//        class WhenDeleteDeliveryManIsCalledWithSuccess {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final Long deliveryManId = 1L;
//                doNothing().when(deliveryManService).delete(deliveryManId);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery-men/" + deliveryManId).header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return 204")
//            void thenShouldReturn204() {
//                assertEquals(204, result.getResponse().getStatus());
//            }
//        }
//
//        @Nested
//        @DisplayName("When delete delivery man is called with error")
//        class WhenDeleteDeliveryManIsCalledWithError {
//            private MvcResult result;
//
//            @BeforeEach
//            void setUp() throws Exception {
//                final Long deliveryManId = 1L;
//
//                doThrow(new IllegalArgumentException("DeliveryMan not found"))
//                        .when(deliveryManService)
//                        .delete(deliveryManId);
//
//                result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery-men/" + deliveryManId).header("Content-Type", "application/json")).andReturn();
//            }
//
//            @Test
//            @DisplayName("Then should return 404")
//            void thenShouldReturn404() {
//                assertEquals(404, result.getResponse().getStatus());
//            }
//        }
//    }
//}
