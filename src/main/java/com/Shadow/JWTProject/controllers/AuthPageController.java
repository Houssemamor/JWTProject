package com.Shadow.JWTProject.controllers;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import com.Shadow.JWTProject.dto.RegisterForm;
import com.Shadow.JWTProject.models.ERole;
import com.Shadow.JWTProject.models.Role;
import com.Shadow.JWTProject.models.User;
import com.Shadow.JWTProject.repositories.RoleRepository;
import com.Shadow.JWTProject.repositories.UserRepository;

/**
 * MVC controller responsible for rendering the Thymeleaf login/register pages.
 *
 * Pattern: Controller (Spring MVC). Keeps UI auth concerns separate from the REST
 * controller under /api/auth.
 */
@Controller
public class AuthPageController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthPageController(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/products/all";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        // Spring Security handles POST /login.
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("registerForm") RegisterForm form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (userRepository.existsByUsername(form.getUsername())) {
            model.addAttribute("registerError", "Username is already taken.");
            return "auth/register";
        }

        if (userRepository.existsByEmail(form.getEmail())) {
            model.addAttribute("registerError", "Email is already in use.");
            return "auth/register";
        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException(
                        "ROLE_USER is missing. Ensure roles are seeded in the database."));

        User user = new User(form.getUsername(), form.getEmail(), passwordEncoder.encode(form.getPassword()));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);

        return "redirect:/login?registered=true";
    }
}
