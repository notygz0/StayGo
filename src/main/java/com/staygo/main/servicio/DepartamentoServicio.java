package com.staygo.main.servicio;

import com.staygo.main.dto.DepartamentoRequest;
import com.staygo.main.dto.DepartamentoResponse;
import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.EstadoReserva;
import com.staygo.main.entity.Reserva;
import com.staygo.main.entity.User;
import com.staygo.main.repository.DepartamentoRepository;
import com.staygo.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServicio {
    private final DepartamentoRepository departamentoRepository;
    private final UserRepository userRepository;
    private final ReservaService reservaService;

    public ResponseEntity<?> crearDepartamento(DepartamentoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User dueno = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Departamento departamento = Departamento.builder()
                .dueno(dueno)
                .nombre(request.getNombre())
                .direccion(request.getDireccion())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .numHabitaciones(request.getNumHabitaciones())
                .build();
        if (request.getImagen() != null && !request.getImagen().isEmpty()) {
            try {
                departamento.setImagen(request.getImagen().getBytes());
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Error al procesar la imagen");
            }
        }
        departamentoRepository.save(departamento);
        return ResponseEntity.ok("Departamento creado exitosamente");
    }
    public ResponseEntity<?> listarDepartamentosDeUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User dueno = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Departamento> departamentos = dueno.getDepartamentos();
        return getListDepartamento(departamentos);
    }
    public ResponseEntity<?> listarDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return getListDepartamento(departamentos);
    }

    private ResponseEntity<?> getListDepartamento(List<Departamento> departamentos) {
        List<DepartamentoResponse> response = departamentos.stream()
                .map(departamento -> DepartamentoResponse.builder()
                        .id(departamento.getId())
                        .nombre(departamento.getNombre())
                        .dueno(departamento.getDueno().getUsername())
                        .direccion(departamento.getDireccion())
                        .descripcion(departamento.getDescripcion())
                        .precio(departamento.getPrecio())
                        .numHabitaciones(departamento.getNumHabitaciones())
                        .imagen(departamento.getImagen() != null ?
                                Base64.getEncoder().encodeToString(departamento.getImagen()) : null)
                        .build())
                .toList();
        return ResponseEntity.ok().body(response);
    }
    public ResponseEntity<?> borrarDepartamento(Integer id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        if (!departamento.getReservas().isEmpty()) {
            reservaService.borrarReservaPorDepartamento(id);
        }
        departamentoRepository.delete(departamento);
        return ResponseEntity.ok("Departamento eliminado exitosamente");
    }
}