package com.Shadow.JWTProject;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Shadow.JWTProject.models.ERole;
import com.Shadow.JWTProject.models.Role;
import com.Shadow.JWTProject.models.User;
import com.Shadow.JWTProject.repositories.RoleRepository;
import com.Shadow.JWTProject.repositories.UserRepository;
import com.Shadow.JWTProject.services.UserDetailsServiceImp;

@SpringBootTest
class JwtProjectApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsServiceImp userDetailsService;

	@Test
	void contextLoads() {
	}

	@Test
	void loadUserByUsernameLoadsRolesWithoutLazyInitializationException() {
		String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		String username = "u" + suffix;
		String email = "u" + suffix + "@example.com";

		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new IllegalStateException("ROLE_USER must exist"));

		User user = new User(username, email, passwordEncoder.encode("password"));
		user.setRoles(Set.of(userRole));
		userRepository.save(user);

		UserDetails loaded = userDetailsService.loadUserByUsername(username);
		Assertions.assertNotNull(loaded);
		Assertions.assertTrue(
				loaded.getAuthorities().stream().anyMatch(a -> "ROLE_USER".equals(a.getAuthority())),
				"Expected ROLE_USER authority to be present");
	}

}
