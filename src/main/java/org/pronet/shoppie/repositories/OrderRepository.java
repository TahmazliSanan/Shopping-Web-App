package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
