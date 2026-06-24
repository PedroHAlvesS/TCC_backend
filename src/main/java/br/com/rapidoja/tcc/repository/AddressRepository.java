package br.com.rapidoja.tcc.repository;

import br.com.rapidoja.tcc.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAndNumberAndNeighborhoodAndZipCodeAndComplement(
            String street, String number, String neighborhood, String zipCode, String complement);
}
