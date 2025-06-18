package org.staygo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.staygo.servicio.UserServicio;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final UserServicio userServicio;

    @GetMapping
    public String home(Model model) {
        userServicio.EstadoUsuario(model);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
