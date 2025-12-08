package com.Shadow.JWTProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Shadow.JWTProject.models.ERole;
import com.Shadow.JWTProject.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

}
