package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Long productId, Long userId);
    List<Cart> getListByUser(Long id);
}
