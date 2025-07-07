package com.staygo.main.controller;

import com.staygo.main.servicio.UserServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {
    private final UserServicio userServicio;
    @GetMapping("/info")
    public ResponseEntity<?> obtenerPerfil() {
        return userServicio.obtenerInformacionUsuario();
    }
}