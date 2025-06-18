package org.staygo.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.staygo.entity.Departamento;
import org.staygo.entity.EstadoReserva;
import org.staygo.entity.Reserva;
import org.staygo.entity.User;
import org.staygo.repository.DepartamentoRepository;
import org.staygo.repository.ReservaRepository;
import org.staygo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final UserRepository userRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ReservaRepository reservaRepository;

    public ResponseEntity<?> crearReservaDepartamento(Integer idDepartamento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername("Cuervas")
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Departamento departamento = departamentoRepository.findById(idDepartamento)
            .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        Reserva reserva = Reserva.builder()
                .user(user)
                .departamento(departamento)
                .fecha_inicio(java.time.LocalDate.now())
                .fecha_final(java.time.LocalDate.now().plusDays(2))
                .estadoReserva(EstadoReserva.PENDIENTE)
                .build();
        reservaRepository.save(reserva);
        return ResponseEntity.ok("Reserva generado exitosamente");
    }
}
