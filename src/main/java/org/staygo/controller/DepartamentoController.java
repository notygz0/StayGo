package org.staygo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.staygo.dto.DepartamentoRequest;
import org.staygo.servicio.DepartamentoServicio;

@Controller
@RequestMapping("/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {
    private final DepartamentoServicio departamentoServicio;

    @GetMapping
    public String Departamentos() {
        return "departamento";
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearDepartamento(@RequestBody DepartamentoRequest request) {
        return departamentoServicio.crearDepartamento(request);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listarDepartamentos() {
        return departamentoServicio.listarDepartamentos();
    }

    @GetMapping("/detalle")
    public String mostrarDetalleDepartamento() {
        return "departamento_detalle";
    }
}
