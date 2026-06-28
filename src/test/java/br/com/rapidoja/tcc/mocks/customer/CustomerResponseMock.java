package br.com.rapidoja.tcc.mocks.customer;

import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
import br.com.rapidoja.tcc.model.Profile;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerResponseMock {
    public static CustomerResponseDTO getCustomerResponseDTO() {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setProfile(Profile.CUSTOMER.getProfileName());
        customerResponseDTO.setCreatedAt(LocalDateTime.now());
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
