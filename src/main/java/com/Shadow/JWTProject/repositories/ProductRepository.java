package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Eagerly load subcategory and provider to avoid LazyInitializationException
    // when accessing these relationships in the view layer.
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.subcategory LEFT JOIN FETCH p.provider")
    List<Product> findAllWithRelations();
}
