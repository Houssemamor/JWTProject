package com.Shadow.JWTProject.services;

import com.Shadow.JWTProject.entities.Category;
import com.Shadow.JWTProject.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }

    public Category create(Category category) {
        return repo.save(category);
    }

    public Category update(Long id, Category category) {
        Category existing = getById(id);
        existing.setTitle(category.getTitle());
        existing.setDescription(category.getDescription());
        existing.setSubcategories(category.getSubcategories());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
