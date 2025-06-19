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

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServicio {
    private final DepartamentoRepository departamentoRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<DepartamentoResponse>> listarDepartamentos() {
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
    public ResponseEntity<DepartamentoResponse> crearDepartamento(DepartamentoRequest departamento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User dueno = userRepository.findByUsername("Cuervas") // nombre por defecto para pruebas (apodo de compañero del repositorio del otro ramo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Departamento nuevoDepartamento = Departamento.builder()
                .dueno(dueno)
                .nombre(departamento.getNombre())
                .descripcion(departamento.getDescripcion())
                .precio(departamento.getPrecio())
                .numHabitaciones(departamento.getNumHabitaciones())
                .build();

        Departamento guardado = departamentoRepository.save(nuevoDepartamento);

        DepartamentoResponse response = DepartamentoResponse.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .descripcion(guardado.getDescripcion())
                .precio(guardado.getPrecio())
                .numHabitaciones(guardado.getNumHabitaciones())
                .dueno(dueno.getUsername())
                .build();

        return ResponseEntity.ok(response);
    }

}
