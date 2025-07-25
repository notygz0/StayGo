package com.staygo.main.controller;

import com.staygo.main.dto.LoginRequest;
import com.staygo.main.dto.RegisterRequest;
import com.staygo.main.servicio.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, Model model, HttpServletRequest request) {
        boolean success = authService.login(loginRequest.getUsername(), loginRequest.getPassword(), request);
        if (success) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Credenciales incorrectas. Por favor, intenta de nuevo.");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request, Model model) {
        boolean success = authService.register(request);
        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Registro fallido. Por favor, verifica tus datos o ya hay una cuenta existente");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}