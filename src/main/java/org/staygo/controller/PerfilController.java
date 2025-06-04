package org.staygo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @GetMapping
    public String verPerfil() {
        return "perfil";
    }
}