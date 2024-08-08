package org.pronet.shoppie.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.OrderService;
import org.pronet.shoppie.services.ProductService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Override
    public void getUserDetails(Principal principal, Model model) {
        super.getUserDetails(principal, model);
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "admin/admin-dashboard";
    }

    @GetMapping("/category-list")
    public String categoryListPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            Model model) {
        Page<Category> page = categoryService.getList(pageNumber, pageSize);
        List<Category> categoryList = page.getContent();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryListSize", categoryList.size());
        model.addAttribute("pageNumber", page.getNumber());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("isFirstPage", page.isFirst());
        model.addAttribute("isLastPage", page.isLast());
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
            return "redirect:/admin/add-product-view";
        } else {
            Product savedProduct = productService.add(product, file);
            if (!ObjectUtils.isEmpty(savedProduct)) {
                session.setAttribute("successMessage", "Product is added successfully!");
            } else {
                session.setAttribute("errorMessage", "Product is not added!");
            }
        }
        return "redirect:/admin/add-product-view";
    }

    @GetMapping("/product-list")
    public String productListPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "") String character,
            Model model) {
        Page<Product> page;
        if (character != null && !character.isEmpty()) {
            page = productService.searchProduct(pageNumber, pageSize, character);
        } else {
            page = productService.getList(pageNumber, pageSize);
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

    @GetMapping("/user-list")
    public String userListPage(Model model) {
        List<UserEntity> userList = userService.getList("User");
        model.addAttribute("userList", userList);
        return "admin/user/user-list";
    }

    @GetMapping("/edit-account-status")
    public String editAccountStatusPage(
            @RequestParam Boolean status,
            @RequestParam Long id,
            HttpSession session) {
        Boolean result = userService.editAccountStatus(id, status);
        if (result) {
            session.setAttribute("successMessage", "User's account status is updated successfully!");
        } else {
            session.setAttribute("errorMessage", "User's account status is not updated!");
        }
        return "redirect:/admin/user-list";
    }

    @GetMapping("/order-list")
    public String orderListPage(Model model) {
        List<Order> orderList = orderService.getList();
        model.addAttribute("orderList", orderList);
        model.addAttribute("search", false);
        return "admin/order/order-list";
    }

    @PostMapping("/update-order-status")
    public String updateOrderStatusPage(
            @RequestParam Long id,
            @RequestParam Integer status,
            HttpSession session) throws MessagingException, UnsupportedEncodingException {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        if (!ObjectUtils.isEmpty(updatedOrder)) {
            session.setAttribute("successMessage", "Status is updated successfully!");
        } else {
            session.setAttribute("errorMessage", "Status is not updated!");
        }
        return "redirect:/admin/order-list";
    }

    @GetMapping("/search-order")
    public String searchOrderPage(
            @RequestParam String orderId,
            Model model,
            HttpSession session) {
        if (orderId != null && !orderId.isEmpty()) {
            Order foundedOrder = orderService.getByOrderId(orderId.trim());
            if (ObjectUtils.isEmpty(foundedOrder)) {
                session.setAttribute("errorMessage", "Order ID is not available!");
                model.addAttribute("orderDetails", null);
            } else {
                model.addAttribute("orderDetails", foundedOrder);
            }
            model.addAttribute("search", true);
        } else {
            List<Order> orderList = orderService.getList();
            model.addAttribute("orderList", orderList);
            model.addAttribute("search", false);
        }
        return "admin/order/order-list";
    }
}
