package br.com.rapidoja.tcc.mocks.customer;

import br.com.rapidoja.tcc.dto.customer.CustomerUpdateDTO;

public class CustomerUpdateMock {
    public static CustomerUpdateDTO getCustomerUpdateDTO() {
        CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO();
        customerUpdateDTO.setEmail("email@email.com");
        customerUpdateDTO.setPassword("secretPassword");
        customerUpdateDTO.setPhoneNumber("31987654321");
        return customerUpdateDTO;
    }
}
