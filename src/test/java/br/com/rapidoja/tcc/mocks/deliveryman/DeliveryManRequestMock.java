package br.com.rapidoja.tcc.mocks.deliveryman;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;

public class DeliveryManRequestMock {
    public static DeliveryManRequestDTO getDeliveryManRequestDTO() {
        DeliveryManRequestDTO deliveryManRequestDTO = new DeliveryManRequestDTO();
        deliveryManRequestDTO.setEmail("email@email.com");
        deliveryManRequestDTO.setPassword("secretPassword");
        deliveryManRequestDTO.setName("Teste Name");
        deliveryManRequestDTO.setPhoneNumber("31987654321");
        return deliveryManRequestDTO;
    }
}
