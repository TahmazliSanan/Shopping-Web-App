package org.pronet.shoppie.controllers;

import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categoryList", categoryService.getActiveTopCategoryList());
        model.addAttribute("productList", productService.getActiveTopProductList());
        return "home/home";
    }
}
