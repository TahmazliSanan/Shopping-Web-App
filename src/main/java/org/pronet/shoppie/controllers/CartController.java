package org.pronet.shoppie.controllers;

import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/add-product")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam Long userId,
            HttpSession session) {
        Cart savedCart = cartService.addToCart(productId, userId);
        if (!ObjectUtils.isEmpty(savedCart)) {
            session.setAttribute("successMessage", "Product is added successfully to your cart!");
        } else {
            session.setAttribute("errorMessage", "Product is not added to your cart!");
        }
        return "redirect:/product/details/" + productId;
    }
}
