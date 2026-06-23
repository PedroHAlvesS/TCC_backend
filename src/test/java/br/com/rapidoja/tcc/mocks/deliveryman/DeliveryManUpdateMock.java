package br.com.rapidoja.tcc.mocks.deliveryman;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManUpdateDTO;

public class DeliveryManUpdateMock {
    public static DeliveryManUpdateDTO getDeliveryManUpdateDTO() {
        DeliveryManUpdateDTO deliveryManUpdateDTO = new DeliveryManUpdateDTO();
        deliveryManUpdateDTO.setEmail("email@email.com");
        deliveryManUpdateDTO.setPassword("secretPassword");
        deliveryManUpdateDTO.setPhoneNumber("31987654321");
        return deliveryManUpdateDTO;
    }
}
