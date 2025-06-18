package org.staygo.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.staygo.dto.DepartamentoRequest;
import org.staygo.dto.DepartamentoResponse;
import org.staygo.entity.Departamento;
import org.staygo.entity.User;
import org.staygo.repository.DepartamentoRepository;
import org.staygo.repository.UserRepository;

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
                .build()
            )
            .toList();
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> crearDepartamento(DepartamentoRequest departamento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User dueno = userRepository.findByUsername("Cuervas")
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Departamento nuevoDepartamento = Departamento.builder()
            .dueno(dueno)
            .nombre(departamento.getNombre())
            .descripcion(departamento.getDescripcion())
            .precio(departamento.getPrecio())
            .numHabitaciones(departamento.getNumHabitaciones())
            .build();

        departamentoRepository.save(nuevoDepartamento);
        return ResponseEntity.ok("Departamento creado exitosamente");
    }
}
