package com.Shadow.JWTProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import com.Shadow.JWTProject.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Security needs roles during authentication to build authorities.
    // Fetch them eagerly here to avoid LazyInitializationException.
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);

    // Accept username or email as the login identifier.
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
