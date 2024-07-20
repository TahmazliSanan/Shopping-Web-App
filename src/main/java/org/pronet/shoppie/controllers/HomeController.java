package org.pronet.shoppie.controllers;

import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Override
    public void getUserDetails(Principal principal, Model model) {
        super.getUserDetails(principal, model);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categoryList", categoryService.getActiveTopCategoryList());
        model.addAttribute("productList", productService.getActiveTopProductList());
        return "home/home";
    }
}
