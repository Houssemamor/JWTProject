package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
