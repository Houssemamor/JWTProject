package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
