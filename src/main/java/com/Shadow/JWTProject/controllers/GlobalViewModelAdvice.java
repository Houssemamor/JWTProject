package com.Shadow.JWTProject.controllers;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GlobalViewModelAdvice {

    @ModelAttribute
    public void addCommonViewAttributes(Model model, HttpServletRequest request, Authentication authentication) {
        String requestUri = request.getRequestURI();
        if (requestUri == null) {
            requestUri = "";
        }

        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        boolean isAuthPage = requestUri.startsWith("/login") || requestUri.startsWith("/register");
        boolean isElevated = request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_MODERATOR");

        model.addAttribute("requestUri", requestUri);
        model.addAttribute("isAuthPage", isAuthPage);
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isElevated", isElevated);
    }
}
