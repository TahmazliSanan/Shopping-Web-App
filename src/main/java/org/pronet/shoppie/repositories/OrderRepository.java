package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(String orderId);
    Page<Order> findByUserId(Long id, Pageable pageable);
    Page<Order> findByOrderId(Pageable pageable, String orderId);
}
