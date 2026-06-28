package br.com.rapidoja.tcc.service.impl;

import br.com.rapidoja.tcc.dto.order.OrderAddressDTO;
import br.com.rapidoja.tcc.mocks.order.OrderAddressMock;
import br.com.rapidoja.tcc.model.Address;
import br.com.rapidoja.tcc.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Nested
    @DisplayName("Given findOrCreate is called")
    class GivenFindOrCreateIsCalled {
        @Nested
        @DisplayName("When address already exists")
        class WhenAddressAlreadyExists {
            final OrderAddressDTO addressDTO = OrderAddressMock.getAddress();
            final Address existingAddress = new Address();
            private Address result;

            @BeforeEach
            void setUp() {
                when(addressRepository.findByStreetAndNumberAndNeighborhoodAndZipCodeAndComplement(
                        addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getNeighborhood(),
                        addressDTO.getZipCode(), addressDTO.getComplement()
                )).thenReturn(Optional.of(existingAddress));

                result = addressService.findOrCreate(addressDTO);
            }

            @Test
            @DisplayName("Then should return existing address")
            void thenShouldReturnExistingAddress() {
                assertEquals(existingAddress, result);
            }

            @Test
            @DisplayName("Then should call repository to find address")
            void thenShouldCallRepositoryToFindAddress() {
                verify(addressRepository).findByStreetAndNumberAndNeighborhoodAndZipCodeAndComplement(
                        addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getNeighborhood(),
                        addressDTO.getZipCode(), addressDTO.getComplement()
                );
            }
        }

        @Nested
        @DisplayName("When address does not exist")
        class WhenAddressDoesNotExist {
            final OrderAddressDTO addressDTO = OrderAddressMock.getAddress();
            private Address result;

            @BeforeEach
            void setUp() {
                when(addressRepository.findByStreetAndNumberAndNeighborhoodAndZipCodeAndComplement(
                        addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getNeighborhood(),
                        addressDTO.getZipCode(), addressDTO.getComplement()
                )).thenReturn(Optional.empty());

                when(addressRepository.save(org.mockito.ArgumentMatchers.any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

                result = addressService.findOrCreate(addressDTO);
            }

            @Test
            @DisplayName("Then should call repository to save new address")
            void thenShouldCallRepositoryToSaveNewAddress() {
                verify(addressRepository).save(org.mockito.ArgumentMatchers.any(Address.class));
            }

            @Test
            @DisplayName("Then should return new address")
            void thenShouldReturnNewAddress() {
                assertEquals(addressDTO.getStreet(), result.getStreet());
                assertEquals(addressDTO.getNumber(), result.getNumber());
                assertEquals(addressDTO.getNeighborhood(), result.getNeighborhood());
                assertEquals(addressDTO.getZipCode(), result.getZipCode());
                assertEquals(addressDTO.getComplement(), result.getComplement());
            }
        }
    }
}
