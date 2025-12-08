package com.Shadow.JWTProject.controllers;

import com.Shadow.JWTProject.entities.Category;
import com.Shadow.JWTProject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("categoryForm", new Category());
        return "categories/category_form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("categoryForm") Category category) {
        categoryService.create(category);
        return "redirect:/categories/all";
    }

    @GetMapping("/all")
    public String listCategories(Model model) {
        List<Category> listCategories = categoryService.getAll();
        model.addAttribute("listCategories", listCategories);
        return "categories/categories_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getById(id);
        model.addAttribute("categoryForm", category);
        return "categories/category_edit_form";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("categoryForm") Category category) {
        categoryService.update(category.getId(), category);
        return "redirect:/categories/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories/all";
    }
}
