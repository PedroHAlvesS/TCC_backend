package br.com.rapidoja.tcc.mocks.order;

import br.com.rapidoja.tcc.dto.order.OrderAddressDTO;

public class OrderAddressMock {
    public static OrderAddressDTO getAddress() {
        OrderAddressDTO addressDTO = new OrderAddressDTO();
        addressDTO.setNeighborhood("neighborhood");
        addressDTO.setNumber("1");
        addressDTO.setStreet("street");
        addressDTO.setZipCode("zipCode");
        return addressDTO;
    }
}
