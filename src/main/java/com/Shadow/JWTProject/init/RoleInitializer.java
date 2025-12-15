package com.Shadow.JWTProject.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Shadow.JWTProject.models.ERole;
import com.Shadow.JWTProject.models.Role;
import com.Shadow.JWTProject.repositories.RoleRepository;

/**
 * Initializes required roles in the database on application startup.
 * Uses CommandLineRunner to ensure roles exist before any authentication requests.
 */
@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize all required roles if they don't exist
        initializeRole(ERole.ROLE_USER);
        initializeRole(ERole.ROLE_MODERATOR);
        initializeRole(ERole.ROLE_ADMIN);
    }

    /**
     * Creates a role if it doesn't already exist in the database.
     * 
     * @param roleName the role enum to initialize
     */
    private void initializeRole(ERole roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role(roleName);
            roleRepository.save(role);
            System.out.println("Initialized role: " + roleName);
        } else {
            System.out.println("Role already exists: " + roleName);
        }
    }
}
