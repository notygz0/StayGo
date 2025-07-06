package com.staygo.main.controller;

import com.staygo.main.dto.ReservaRequest;
import com.staygo.main.servicio.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

}
