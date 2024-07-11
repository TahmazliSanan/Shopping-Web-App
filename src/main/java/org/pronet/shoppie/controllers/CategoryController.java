package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CategoryService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
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

    @GetMapping("/list")
    public String categoryListPage(Model model) {
        List<Category> categoryList = categoryService.getActiveCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "category/category-list";
    }
}
