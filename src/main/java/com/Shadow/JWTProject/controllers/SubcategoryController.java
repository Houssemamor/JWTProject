package com.Shadow.JWTProject.controllers;

import com.Shadow.JWTProject.entities.Subcategory;
import com.Shadow.JWTProject.services.SubcategoryService;
import com.Shadow.JWTProject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subcategories")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService, CategoryService categoryService) {
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subcategoryForm", new Subcategory());
        model.addAttribute("listCategories", categoryService.getAll());
        return "subcategories/subcategory_form";
    }

    @PostMapping("/save")
    public String saveSubcategory(@ModelAttribute("subcategoryForm") Subcategory subcategory) {
        subcategoryService.create(subcategory);
        return "redirect:/subcategories/all";
    }

    @GetMapping("/all")
    public String listSubcategories(Model model) {
        model.addAttribute("listSubcategories", subcategoryService.getAll());
        return "subcategories/subcategories_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Subcategory subcategory = subcategoryService.getById(id);
        model.addAttribute("subcategoryForm", subcategory);
        model.addAttribute("listCategories", categoryService.getAll());
        return "subcategories/subcategory_edit_form";
    }

    @PostMapping("/update")
    public String updateSubcategory(@ModelAttribute("subcategoryForm") Subcategory subcategory) {
        subcategoryService.update(subcategory.getId(), subcategory);
        return "redirect:/subcategories/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubcategory(@PathVariable Long id) {
        subcategoryService.delete(id);
        return "redirect:/subcategories/all";
    }
}
