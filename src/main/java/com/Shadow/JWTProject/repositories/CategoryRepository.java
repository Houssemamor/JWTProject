package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
