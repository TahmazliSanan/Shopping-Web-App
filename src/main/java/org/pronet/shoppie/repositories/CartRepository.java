package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByUserId(Long userId);
    Long countByUserId(Long id);
}
