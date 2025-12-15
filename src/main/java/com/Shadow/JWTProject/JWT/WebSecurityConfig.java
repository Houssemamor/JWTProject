package com.Shadow.JWTProject.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Shadow.JWTProject.services.UserDetailsServiceImp;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImp userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        // API is stateless and secured by JWT (Authorization: Bearer <token>).
        http.securityMatcher("/api/**");

        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain uiFilterChain(HttpSecurity http) throws Exception {
        // UI is a normal browser app (Thymeleaf). Use session-based auth with form login.
        http.securityMatcher("/**");

        http.authorizeHttpRequests(auth -> auth
                        // Permit auth pages even if the container appends a path parameter like ;jsessionid.
                        // Without this, /login;jsessionid=... would not match "/login" and can cause infinite redirects.
                        .requestMatchers("/login", "/login*", "/register", "/register*").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        // Always allow the default error page.
                        .requestMatchers("/error").permitAll()

                        // Product "read" routes: ROLE_USER can list products.
                        .requestMatchers(HttpMethod.GET, "/products/all").hasAnyRole("USER", "MODERATOR", "ADMIN")

                        // Product "write" routes: restrict to elevated roles.
                        // Note: delete is currently a GET endpoint in MVC.
                        .requestMatchers("/products/add", "/products/save", "/products/update").hasAnyRole("MODERATOR", "ADMIN")
                        .requestMatchers("/products/edit/**", "/products/delete/**").hasAnyRole("MODERATOR", "ADMIN")

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/products/all", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                // Use IF_REQUIRED so UI can create sessions.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
