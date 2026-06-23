package br.com.rapidoja.tcc.mocks;

import br.com.rapidoja.tcc.dto.CustomerResponseDTO;
import br.com.rapidoja.tcc.model.Profile;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerResponseMock {
    public static CustomerResponseDTO getCustomerResponseDTO() {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setProfile(Profile.CUSTOMER);
        customerResponseDTO.setCreatedAt(LocalDateTime.now());
        customerResponseDTO.setUpdatedAt(LocalDateTime.now());
        customerResponseDTO.setEnabled(true);
        customerResponseDTO.setId(1L);
        customerResponseDTO.setName("name");
        customerResponseDTO.setEmail("email");
        customerResponseDTO.setPhoneNumber("31987654321");
        return customerResponseDTO;
    }

    public static List<CustomerResponseDTO> getCustomerResponseList() {
        return List.of(getCustomerResponseDTO());
    }
}
