package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
