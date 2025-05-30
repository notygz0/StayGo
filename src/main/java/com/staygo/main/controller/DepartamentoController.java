package com.staygo.main.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @GetMapping
    public String listarDepartamentos() {
        return "departamentos";
    }
}