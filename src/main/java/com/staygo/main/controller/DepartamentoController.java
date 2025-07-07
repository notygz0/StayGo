package com.staygo.main.controller;

import com.staygo.main.dto.DepartamentoRequest;
import com.staygo.main.servicio.DepartamentoServicio;
import com.staygo.main.servicio.ReservaService;
import com.staygo.main.servicio.UserServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {
    private final UserServicio userServicio;
    private final DepartamentoServicio departamentoServicio;
    private final ReservaService reservaService;

    @GetMapping()
    public String Departamentos() {
        return "departamento";
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crearDepartamento(@ModelAttribute DepartamentoRequest request) {
        return departamentoServicio.crearDepartamento(request);
    }

    @GetMapping("/lista/user")
    public ResponseEntity<?> listarDepartamentosDeUsuario() {
        return departamentoServicio.listarDepartamentosDeUsuario();
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listarDepartamentos() {
        return departamentoServicio.listarDepartamentos();
    }

    @GetMapping("/detalle")
    public String mostrarDetalleDepartamento(Model model,@RequestParam("id") Integer idAlojamiento) {
        boolean estado = reservaService.AlojamientoReservado(idAlojamiento, 1);
        userServicio.EstadoUsuario(model);
        model.addAttribute("estado", estado);
        return "departamento_detalle";
    }
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarDepartamento(@PathVariable Integer id) {
        return departamentoServicio.borrarDepartamento(id);
    }
}