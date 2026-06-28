package br.com.rapidoja.tcc.mocks.deliveryman;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
import br.com.rapidoja.tcc.model.Profile;

import java.time.LocalDateTime;
import java.util.List;

public class DeliveryManResponseMock {
    public static DeliveryManResponseDTO getDeliveryManResponseDTO() {
        DeliveryManResponseDTO deliveryManResponseDTO = new DeliveryManResponseDTO();
        deliveryManResponseDTO.setProfile(Profile.DELIVERY_MAN.getProfileName());
        deliveryManResponseDTO.setCreatedAt(LocalDateTime.now());
        deliveryManResponseDTO.setId(1L);
        deliveryManResponseDTO.setName("name");
        deliveryManResponseDTO.setEmail("email");
        deliveryManResponseDTO.setPhoneNumber("31987654321");
        return deliveryManResponseDTO;
    }

    public static List<DeliveryManResponseDTO> getDeliveryManResponseList() {
        return List.of(getDeliveryManResponseDTO());
    }
}
