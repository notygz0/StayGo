package com.staygo.main.controller;

import com.staygo.main.dto.ReservaRequest;
import com.staygo.main.servicio.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReservaDepartamento(@ModelAttribute ReservaRequest idDepartamento) {
        return reservaService.crearReservaDepartamento(idDepartamento);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listarReservasDepartamento() {
        return reservaService.listarReservas();
    }

    @GetMapping("/departamento/{id}")
    public ResponseEntity<?> ReservaDepartamento(@PathVariable Integer id) {
        return reservaService.obtenerReservaDepartamento(id);
    }
    @GetMapping("/cambiarEstado/{id}")
    public ResponseEntity<?> cambiarEstadoReserva(@PathVariable Integer id, @RequestParam("estado") String estado) {
        return reservaService.CambiarEstadoReserva(id, estado);
    }
}