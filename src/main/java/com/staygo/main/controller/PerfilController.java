package com.staygo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @GetMapping
    public String verPerfil() {
        return "perfil";
    }
}