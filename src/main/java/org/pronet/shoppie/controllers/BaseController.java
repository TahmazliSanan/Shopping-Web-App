package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CartService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userService.getUserByEmail(email);
            if (user != null) {
                model.addAttribute("user", user);
                Long countCart = cartService.getCountCart(user.getId());
                model.addAttribute("countCart", countCart);
            } else {
                model.addAttribute("user", null);
                model.addAttribute("countCart", 0L);
            }
        }
    }
}
