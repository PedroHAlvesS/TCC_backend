package br.com.rapidoja.tcc.repository;

import br.com.rapidoja.tcc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findByCustomerId(Long customerId);

    @Query("SELECT o FROM Order o WHERE o.deliveryMan.id = :deliveryManId")
    List<Order> findByDeliveryManId(Long deliveryManId);

    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findByStatus(String status);
}
