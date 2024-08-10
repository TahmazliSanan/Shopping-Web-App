package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Override
    public void getUserDetails(Principal principal, Model model) {
        super.getUserDetails(principal, model);
    }

    @GetMapping("/list")
    public String productListPage(
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize,
            @RequestParam(name = "character", defaultValue = "") String character,
            Model model) {
        List<Category> categoryList = categoryService.getActiveCategoryList();
        model.addAttribute("parameterValue", category);
        model.addAttribute("categoryList", categoryList);
        Page<Product> page;
        if (character != null && !character.isEmpty()) {
            page = productService.searchActiveProduct(pageNumber, pageSize, category, character);
        } else {
            page = productService.getActiveProductList(pageNumber, pageSize, category);
        }
        List<Product> productList = page.getContent();
        model.addAttribute("productList", productList);
        model.addAttribute("productListSize", productList.size());
        model.addAttribute("pageNumber", page.getNumber());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("isFirstPage", page.isFirst());
        model.addAttribute("isLastPage", page.isLast());
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
