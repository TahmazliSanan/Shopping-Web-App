package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByUserId(Long userId);
    Long countByUserId(Long id);
}
