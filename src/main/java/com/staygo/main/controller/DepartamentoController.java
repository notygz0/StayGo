package com.staygo.main.controller;

import com.staygo.main.dto.DepartamentoRequest;
import com.staygo.main.dto.DepartamentoResponse;
import com.staygo.main.servicio.DepartamentoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {
    private final DepartamentoServicio departamentoServicio;

    @GetMapping()
    public String departamentos() {
        return "departamento";
    }
    @PostMapping("/crear")
    public ResponseEntity<DepartamentoResponse> crearDepartamento(@RequestBody DepartamentoRequest request) {
        return departamentoServicio.crearDepartamento(request);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<DepartamentoResponse>> listarDepartamentos() {
        return departamentoServicio.listarDepartamentos();
    }


    @GetMapping("/detalle")
    public String mostrarDetalleDepartamento() {
        return "departamento_detalle";
    }
}