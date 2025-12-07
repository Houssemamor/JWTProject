package com.Shadow.JWTProject.repositories;

import com.Shadow.JWTProject.models.ERole;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

}
