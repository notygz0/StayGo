package com.staygo.main.servicio;

import com.staygo.main.dto.DepartamentoRequest;
import com.staygo.main.dto.DepartamentoResponse;
import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.User;
import com.staygo.main.repository.DepartamentoRepository;
import com.staygo.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServicio {

    private final DepartamentoRepository departamentoRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> listarDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        List<DepartamentoResponse> response = departamentos.stream()
                .map(departamento -> DepartamentoResponse.builder()
                        .id(departamento.getId())
                        .nombre(departamento.getNombre())
                        .dueno(departamento.getDueno().getUsername())
                        .descripcion(departamento.getDescripcion())
                        .precio(departamento.getPrecio())
                        .numHabitaciones(departamento.getNumHabitaciones())
                        .build())
                .toList();
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> crearDepartamento(DepartamentoRequest departamento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User dueno = userRepository.findByUsername("Cuervas")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Departamento.DepartamentoBuilder builder = Departamento.builder()
                .dueno(dueno)
                .nombre(departamento.getNombre())
                .descripcion(departamento.getDescripcion())
                .precio(departamento.getPrecio())
                .numHabitaciones(departamento.getNumHabitaciones());

        if (departamento.getImagen() != null && !departamento.getImagen().isEmpty()) {
            try {
                builder.imagen(departamento.getImagen().getBytes());
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Error al procesar la imagen");
            }
        }

        departamentoRepository.save(builder.build());
        return ResponseEntity.ok("Departamento creado exitosamente");
    }
}