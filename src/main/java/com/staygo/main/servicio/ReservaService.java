package com.staygo.main.servicio;

import com.staygo.main.dto.ReservaRequest;
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

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final UserRepository userRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ReservaRepository reservaRepository;
    public ResponseEntity<?> crearReservaDepartamento(ReservaRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(request.getIdAlojamiento());
        User user = userRepository.findByUsername("Cuervas")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Departamento departamento = departamentoRepository.findById(request.getIdAlojamiento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        Reserva reserva = Reserva.builder()
                .user(user)
                .departamento(departamento)
                .fecha_inicio(request.getFechaInicio())
                .fecha_final(request.getFechaFin())
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
                                .imagen(reserva.getDepartamento().getImagen() != null ?
                                        Base64.getEncoder().encodeToString(reserva.getDepartamento().getImagen()) : null)
                                .alojamiento(reserva.getDepartamento().getNombre())
                                .fechaInicio(reserva.getFecha_inicio())
                                .fechaFin(reserva.getFecha_final())
                                .estado(String.valueOf(reserva.getEstadoReserva()))
                                .build()
                )
                .toList();
        return ResponseEntity.ok().body(responses);
    }
    public boolean AlojamientoReservado(Integer idAlojamiento, int tipoAlojamiento) {
        if (tipoAlojamiento == 1) { // 1 para Departamento
            List<Reserva> reserva = reservaRepository.findAllByDepartamentoId(idAlojamiento);
            return reserva.isEmpty(); // No hay reservas, alojamiento disponible
        } else if (tipoAlojamiento == 2) { // 2 para Hotel
            List<Reserva> reserva = reservaRepository.findAllByHotelId(idAlojamiento);
            return reserva.isEmpty(); // No hay reservas, alojamiento disponible
        }else {
            throw new IllegalArgumentException("Tipo de alojamiento no válido");
        }
    }
}