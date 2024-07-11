package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userService.getUserByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categoryList", categoryService.getActiveTopCategoryList());
        model.addAttribute("productList", productService.getActiveTopProductList());
        return "home/home";
    }
}
