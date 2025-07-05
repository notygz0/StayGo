package com.staygo.main.servicio;

import com.staygo.main.dto.ReservaResponse;
import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.EstadoReserva;
import com.staygo.main.entity.Reserva;
import com.staygo.main.entity.User;
import com.staygo.main.repository.DepartamentoRepository;
import com.staygo.main.repository.ReservaRepository;
import com.staygo.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResponseEntity <?> listarReservas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername("Cuervas")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Reserva> reservas = reservaRepository.findAllByUsuarioId(user.getId());
        List<ReservaResponse> responses = reservas.stream()
                .map(
                        reserva -> ReservaResponse.builder()
                                .id(reserva.getId())
                                .name(reserva.getUser().getUsername())
                                .alojamiento(reserva.getDepartamento().getNombre())
                                .fechaInicio(reserva.getFecha_inicio())
                                .fechaFin(reserva.getFecha_final())
                                .estado(String.valueOf(reserva.getEstadoReserva()))
                                .build()
                )
                .toList();
        return ResponseEntity.ok().body(responses);
    }


}