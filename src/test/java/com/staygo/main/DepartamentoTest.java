package com.staygo.main;

import com.staygo.main.dto.DepartamentoRequest;
import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.User;
import com.staygo.main.repository.DepartamentoRepository;
import com.staygo.main.repository.UserRepository;
import com.staygo.main.servicio.DepartamentoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartamentoTest {

    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private DepartamentoServicio departamentoServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCrearDepartamento() {
        DepartamentoRequest request = new DepartamentoRequest();
        request.setNombre("Departamento");
        request.setDescripcion("Bonito depto");
        request.setPrecio(1000.0F);
        request.setNumHabitaciones(2);

        User user = new User();
        when(authentication.getName()).thenReturn("Cuervas");
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(user));

        Departamento departamentoGuardado = Departamento.builder()
                .dueno(user)
                .nombre("Departamento")
                .descripcion("Bonito depto")
                .precio(1000.0F)
                .numHabitaciones(2)
                .build();

        when(departamentoRepository.save(any(Departamento.class))).thenReturn(departamentoGuardado);

        ResponseEntity<?> response = departamentoServicio.crearDepartamento(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(departamentoRepository).save(any(Departamento.class));
    }
}