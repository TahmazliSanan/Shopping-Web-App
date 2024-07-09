package org.pronet.shoppie.controllers;

import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "admin/admin-dashboard";
    }

    @GetMapping("/category-list")
    public String categoryListPage(Model model) {
        List<Category> categoryList = categoryService.getList();
        model.addAttribute("categoryList", categoryList);
        return "admin/category/category-list";
    }

    @PostMapping("/add-category")
    public String addCategory(
            @ModelAttribute Category category,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);
        Boolean isCategoryExist = categoryService.existsCategoryByName(category.getName());
        if (isCategoryExist) {
            session.setAttribute("errorMessage", "Category is already exist!");
        } else {
            Category savedCategory = categoryService.add(category, file);
            if (!ObjectUtils.isEmpty(savedCategory)) {
                session.setAttribute("successMessage", "Category is added successfully!");
            } else {
                session.setAttribute("errorMessage", "Category is not added!");
            }
        }
        return "redirect:/admin/category-list";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategoryPage(
            @PathVariable("id") Long id,
            Model model) {
        Category foundedCategory = categoryService.getById(id);
        model.addAttribute("foundedCategory", foundedCategory);
        return "admin/category/edit-category";
    }

    @PostMapping("/edit-category")
    public String editCategory(
            @ModelAttribute Category category,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        Category updatedCategory = categoryService.edit(category, file);
        if (!ObjectUtils.isEmpty(updatedCategory)) {
            session.setAttribute("successMessage", "Category is updated successfully!");
        } else {
            session.setAttribute("errorMessage", "Category is not updated!");
        }
        return "redirect:/admin/category-list";
    }

    @GetMapping("/remove-category/{id}")
    public String removeCategory(
            @PathVariable("id") Long id,
            HttpSession session) {
        Boolean isCategoryRemove = categoryService.remove(id);
        if (isCategoryRemove) {
            session.setAttribute("successMessage", "Category is deleted successfully!");
        } else {
            session.setAttribute("errorMessage", "Category is not deleted!");
        }
        return "redirect:/admin/category-list";
    }

    @GetMapping("/add-product-view")
    public String addProductPage(Model model) {
        List<Category> categoryList = categoryService.getList();
        model.addAttribute("categoryList", categoryList);
        return "admin/product/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(
            @ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setImageName(imageName);
        Boolean isProductExist = productService.existsProductByName(product.getName());
        if (isProductExist) {
            session.setAttribute("errorMessage", "Product is already exist!");
        } else {
            Product savedProduct = productService.add(product, file);
            if (!ObjectUtils.isEmpty(savedProduct)) {
                session.setAttribute("successMessage", "Product is added successfully!");
            } else {
                session.setAttribute("errorMessage", "Product is not added!");
            }
        }
        return "redirect:/admin/product-list";
    }

    @GetMapping("/product-list")
    public String productListPage(Model model) {
        List<Product> productList = productService.getList();
        model.addAttribute("productList", productList);
        return "admin/product/product-list";
    }

    @GetMapping("/edit-product/{id}")
    public String editProductPage(
            @PathVariable("id") Long id,
            Model model) {
        List<Category> categoryList = categoryService.getList();
        Product foundedProduct = productService.getById(id);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("foundedProduct", foundedProduct);
        return "admin/product/edit-product";
    }

    @PostMapping("/edit-product")
    public String editProduct(
            @ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        Product updatedProduct = productService.edit(product, file);
        if (!ObjectUtils.isEmpty(updatedProduct)) {
            session.setAttribute("successMessage", "Product is updated successfully!");
        } else {
            session.setAttribute("errorMessage", "Product is not updated!");
        }
        return "redirect:/admin/product-list";
    }

    @GetMapping("/remove-product/{id}")
    public String removeProduct(
            @PathVariable("id") Long id,
            HttpSession session) {
        Boolean isProductRemove = productService.remove(id);
        if (isProductRemove) {
            session.setAttribute("successMessage", "Product is deleted successfully!");
        } else {
            session.setAttribute("errorMessage", "Product is not deleted!");
        }
        return "redirect:/admin/product-list";
    }
}
