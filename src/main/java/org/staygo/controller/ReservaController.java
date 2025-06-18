package org.staygo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.staygo.servicio.ReservaService;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReservaDepartamento(@ModelAttribute Integer idDepartamento) {
        return reservaService.crearReservaDepartamento(idDepartamento);
    }

    @GetMapping
    public String listarReservas() {
        return "reservas";
    }
}
