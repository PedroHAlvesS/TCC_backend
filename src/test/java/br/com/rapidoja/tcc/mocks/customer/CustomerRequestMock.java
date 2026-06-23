package br.com.rapidoja.tcc.mocks.customer;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;

public class CustomerRequestMock {
    public static CustomerRequestDTO getCustomerRequestDTO() {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setEmail("email@email.com");
        customerRequestDTO.setPassword("secretPassword");
        customerRequestDTO.setName("Teste Name");
        customerRequestDTO.setPhoneNumber("31987654321");
        return customerRequestDTO;
    }
}
