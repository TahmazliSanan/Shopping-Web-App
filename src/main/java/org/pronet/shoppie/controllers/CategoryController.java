package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Override
    public void getUserDetails(Principal principal, Model model) {
        super.getUserDetails(principal, model);
    }

    @GetMapping("/list")
    public String categoryListPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String character,
            Model model) {
        Page<Category> page;
        if (character != null && !character.isEmpty()) {
            page = categoryService.searchActiveCategory(pageNumber, pageSize, character.trim());
        } else {
            page = categoryService.getActiveCategoryList(pageNumber, pageSize);
        }
        List<Category> categoryList = page.getContent();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryListSize", categoryList.size());
        model.addAttribute("pageNumber", page.getNumber());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("isFirstPage", page.isFirst());
        model.addAttribute("isLastPage", page.isLast());
        return "category/category-list";
    }

    @GetMapping("/list/{id}/view-products")
    public String viewProductListPage(
            @PathVariable("id") Long id,
            Model model) {
        Category foundedCategory = categoryService.getById(id);
        List<Product> productList = productService.getActiveProductList(foundedCategory.getName());
        model.addAttribute("productList", productList);
        return "product/view-product-list-by-category";
    }
}
