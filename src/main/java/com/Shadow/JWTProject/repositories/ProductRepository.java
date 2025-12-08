package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
