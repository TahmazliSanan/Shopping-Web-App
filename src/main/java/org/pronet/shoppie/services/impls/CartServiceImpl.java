package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.repositories.CartRepository;
import org.pronet.shoppie.repositories.ProductRepository;
import org.pronet.shoppie.repositories.UserRepository;
import org.pronet.shoppie.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart addToCart(Long productId, Long userId) {
        Optional<UserEntity> foundedUser = userRepository
                .findById(userId);
        Optional<Product> foundedProduct = productRepository
                .findById(productId);
        Cart foundedCart = cartRepository.findByUserIdAndProductId(productId, userId);
        Cart cart;
        if (foundedUser.isPresent() && foundedProduct.isPresent()) {
            UserEntity user = foundedUser.get();
            Product product = foundedProduct.get();

            if (ObjectUtils.isEmpty(foundedCart)) {
                cart = new Cart();
                cart.setProduct(product);
                cart.setUser(user);
                cart.setQuantity(1L);
                cart.setTotalPrice(product.getDiscountPrice());
            } else {
                cart = foundedCart;
                cart.setQuantity(cart.getQuantity() + 1);
                cart.setTotalPrice(cart.getQuantity() * cart.getProduct().getDiscountPrice());
            }
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public List<Cart> getListByUser(Long id) {
        return List.of();
    }
}
