package com.Shadow.JWTProject.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Shadow.JWTProject.models.ERole;
import com.Shadow.JWTProject.models.Role;
import com.Shadow.JWTProject.repositories.RoleRepository;

/**
 * Seeds roles required by the authentication flow.
 *
 * Without this, registration can fail when the roles table is empty.
 *
 * Pattern: Startup initializer (CommandLineRunner).
 */
@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        Arrays.stream(ERole.values()).forEach(this::ensureRole);
    }

    private void ensureRole(ERole roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
