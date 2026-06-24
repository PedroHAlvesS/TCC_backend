package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.order.OrderAddressDTO;
import br.com.rapidoja.tcc.model.Address;
import br.com.rapidoja.tcc.repository.AddressRepository;
import br.com.rapidoja.tcc.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address findOrCreate(OrderAddressDTO addressDTO) {
        Optional<Address> existingAddress = addressRepository
                .findByStreetAndNumberAndNeighborhoodAndZipCodeAndComplement(
                        addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getNeighborhood(), addressDTO.getZipCode(), addressDTO.getComplement());

        if (existingAddress.isPresent()) {
            return existingAddress.get();
        }

        Address newAddress = new Address();
        newAddress.setStreet(addressDTO.getStreet());
        newAddress.setNumber(addressDTO.getNumber());
        newAddress.setNeighborhood(addressDTO.getNeighborhood());
        newAddress.setZipCode(addressDTO.getZipCode());
        newAddress.setComplement(addressDTO.getComplement());

        return addressRepository.save(newAddress);
    }
}
