package org.pronet.shoppie.controllers;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String categoryListPage(Model model) {
        List<Category> categoryList = categoryService.getActiveCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "category/category-list";
    }
}
