package br.com.rapidoja.tcc.service;

import br.com.rapidoja.tcc.dto.order.OrderAddressDTO;
import br.com.rapidoja.tcc.model.Address;

public interface AddressService {

    Address findOrCreate(OrderAddressDTO addressDTO);
}
