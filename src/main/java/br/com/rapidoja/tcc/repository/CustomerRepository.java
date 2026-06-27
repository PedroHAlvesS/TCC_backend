package br.com.rapidoja.tcc.repository;

import br.com.rapidoja.tcc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.enabled = true AND u.profile = 'CUSTOMER'")
    List<User> findAllEnabled();

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.enabled = true AND u.profile = 'CUSTOMER'")
    Optional<User> findEnabledByEmail(String email);

    boolean existsByEmail(String email);
}