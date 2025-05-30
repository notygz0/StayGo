package com.staygo.main.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @GetMapping
    public String listarReservas() {
        return "reservas";
    }
}