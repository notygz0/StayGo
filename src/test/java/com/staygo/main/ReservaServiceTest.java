package com.staygo.main;

import com.staygo.main.dto.ReservaRequest;
import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.EstadoReserva;
import com.staygo.main.entity.Reserva;
import com.staygo.main.entity.User;
import com.staygo.main.repository.DepartamentoRepository;
import com.staygo.main.repository.ReservaRepository;
import com.staygo.main.repository.UserRepository;
import com.staygo.main.servicio.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCrearReservaDepartamento_exito() {
        ReservaRequest request = new ReservaRequest();
        request.setIdAlojamiento(1);
        request.setFechaInicio(java.time.LocalDate.of(2024, 6, 1));
        request.setFechaFin(java.time.LocalDate.of(2024, 6, 10));
        User user = new User();
        Departamento departamento = new Departamento();
        when(authentication.getName()).thenReturn("usuario");
        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(departamento));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(new Reserva());
        ResponseEntity<?> response = reservaService.crearReservaDepartamento(request);
        assertEquals(200, response.getStatusCode().value());
        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    void testCrearReservaDepartamento_usuarioNoEncontrado() {
        ReservaRequest request = new ReservaRequest();
        when(authentication.getName()).thenReturn("noexiste");
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReservaDepartamento(request);
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testCrearReservaDepartamento_departamentoNoEncontrado() {
        ReservaRequest request = new ReservaRequest();
        request.setIdAlojamiento(1);
        User user = new User();
        when(authentication.getName()).thenReturn("usuario");
        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        when(departamentoRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReservaDepartamento(request);
        });
        assertEquals("Departamento no encontrado", exception.getMessage());
    }

    @Test
    void testListarReservas_usuarioConReservas() {
        User user = new User();
        user.setId(1);
        Reserva reserva = new Reserva();
        reserva.setUser(user);
        reserva.setDepartamento(new Departamento());
        when(authentication.getName()).thenReturn("usuario");
        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        when(reservaRepository.findAllByUsuarioId(1)).thenReturn(List.of(reserva));
        ResponseEntity<?> response = reservaService.listarReservas();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testListarReservas_usuarioNoEncontrado() {
        when(authentication.getName()).thenReturn("noexiste");
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.listarReservas();
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testCambiarEstadoReserva_exito() {
        Reserva reserva = new Reserva();
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
        ResponseEntity<?> response = reservaService.CambiarEstadoReserva(1, "CONFIRMADO");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Estado de la reserva actualizado exitosamente", response.getBody());
    }

    @Test
    void testCambiarEstadoReserva_reservaNoEncontrada() {
        when(reservaRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.CambiarEstadoReserva(1, "CONFIRMADA");
        });
        assertEquals("Reserva no encontrada", exception.getMessage());
    }
} 