package br.com.rapidoja.tcc.repository;

import br.com.rapidoja.tcc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.enabled = true AND u.profile = 'ADMIN'")
    List<User> findAllEnabled();

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.enabled = true AND u.profile = 'ADMIN'")
    Optional<User> findEnabledById(Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.enabled = true AND u.profile = 'ADMIN'")
    Optional<User> findEnabledByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.id != :id AND u.enabled = true")
    boolean existsByEmailAndNotId(String email, Long id);
}
