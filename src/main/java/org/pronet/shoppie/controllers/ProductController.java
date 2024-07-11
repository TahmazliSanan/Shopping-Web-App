package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
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

    public ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String productListPage(
            Model model,
            @RequestParam(value = "category", defaultValue = "") String category) {
        List<Category> categoryList = categoryService.getActiveCategoryList();
        List<Product> productList = productService.getActiveProductList(category);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);
        model.addAttribute("parameterValue", category);
        return "product/product-list";
    }

    @GetMapping("/details/{id}")
    public String productDetailsPage(
            @PathVariable("id") Long id,
            Model model) {
        Product foundedProduct = productService.getById(id);
        model.addAttribute("foundedProduct", foundedProduct);
        return "product/product-details";
    }
}
