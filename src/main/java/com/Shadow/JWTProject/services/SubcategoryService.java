package com.Shadow.JWTProject.services;

import com.Shadow.JWTProject.entities.Subcategory;
import com.Shadow.JWTProject.repositories.SubcategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubcategoryService {

    private final SubcategoryRepository repo;

    public SubcategoryService(SubcategoryRepository repo) {
        this.repo = repo;
    }

    public List<Subcategory> getAll() {
        return repo.findAll();
    }

    public Subcategory getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found: " + id));
    }

    public Subcategory create(Subcategory subcategory) {
        return repo.save(subcategory);
    }

    public Subcategory update(Long id, Subcategory subcategory) {
        Subcategory existing = getById(id);
        existing.setTitle(subcategory.getTitle());
        existing.setDescription(subcategory.getDescription());
        existing.setCategory(subcategory.getCategory());
        existing.setProducts(subcategory.getProducts());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
