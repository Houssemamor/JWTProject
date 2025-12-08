package com.Shadow.JWTProject.controllers;

import com.Shadow.JWTProject.entities.Product;
import com.Shadow.JWTProject.services.ProductService;
import com.Shadow.JWTProject.services.SubcategoryService;
import com.Shadow.JWTProject.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SubcategoryService subcategoryService;
    private final ProviderService providerService;

    @Autowired
    public ProductController(ProductService productService,
            SubcategoryService subcategoryService,
            ProviderService providerService) {
        this.productService = productService;
        this.subcategoryService = subcategoryService;
        this.providerService = providerService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("productForm", new Product());
        model.addAttribute("listSubcategories", subcategoryService.getAll());
        model.addAttribute("listProviders", providerService.getAll());
        return "products/product_form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("productForm") Product product) {
        productService.createProduct(product);
        return "redirect:/products/all";
    }

    @GetMapping("/all")
    public String listProducts(Model model) {
        List<Product> listProducts = productService.getAllProducts();
        model.addAttribute("listProducts", listProducts);
        return "products/products_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("productForm", product);
        model.addAttribute("listSubcategories", subcategoryService.getAll());
        model.addAttribute("listProviders", providerService.getAll());
        return "products/product_edit_form";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("productForm") Product product) {
        productService.update(product.getId(), product);
        return "redirect:/products/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products/all";
    }
}