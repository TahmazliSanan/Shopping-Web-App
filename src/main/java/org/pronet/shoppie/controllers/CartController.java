package org.pronet.shoppie.controllers;

import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CartService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    private UserEntity getLoggedInUserDetails(Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            return userService.getUserByEmail(email);
        } else {
            return null;
        }
    }

    @Override
    public void getUserDetails(Principal principal, Model model) {
        super.getUserDetails(principal, model);
    }

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

    @GetMapping("/show-my-cart")
    public String myCartPage(Principal principal, Model model) {
        UserEntity user = getLoggedInUserDetails(principal);
        List<Cart> cartList = cartService.getListByUser(user.getId());
        model.addAttribute("cartList", cartList);
        if (!cartList.isEmpty()) {
            Double totalOrderPrice = cartList.get(cartList.size() - 1).getTotalOrderPrice();
            model.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "cart/my-cart";
    }

    @GetMapping("/update-quantity")
    public String updateCartQuantity(
            @RequestParam String symbol,
            @RequestParam Long cartId) {
        cartService.updateQuantity(symbol, cartId);
        return "redirect:/cart/show-my-cart";
    }
}
