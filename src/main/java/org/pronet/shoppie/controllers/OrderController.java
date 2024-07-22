package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.entities.OrderRequest;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CartService;
import org.pronet.shoppie.services.OrderService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
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

    @GetMapping("/view")
    public String orderPage(Principal principal, Model model) {
        UserEntity user = getLoggedInUserDetails(principal);
        List<Cart> cartList = cartService.getListByUser(user.getId());
        model.addAttribute("cartList", cartList);
        if (!cartList.isEmpty()) {
            Double orderPrice = cartList.get(cartList.size() - 1).getTotalOrderPrice();
            Double totalOrderPrice = cartList.get(cartList.size() - 1).getTotalOrderPrice() + 250 + 100;
            model.addAttribute("orderPrice", orderPrice);
            model.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/order/order";
    }

    @PostMapping("/save")
    public String saveOrder(
            @ModelAttribute OrderRequest orderRequest,
            Principal principal) {
        UserEntity user = getLoggedInUserDetails(principal);
        orderService.saveOrder(user.getId(), orderRequest);
        return "redirect:/order/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "/order/success";
    }
}
