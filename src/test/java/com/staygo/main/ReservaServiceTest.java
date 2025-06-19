package com.staygo.main;

import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.Reserva;
import com.staygo.main.entity.User;
import com.staygo.main.entity.EstadoReserva;
import com.staygo.main.repository.DepartamentoRepository;
import com.staygo.main.repository.ReservaRepository;
import com.staygo.main.repository.UserRepository;
import com.staygo.main.servicio.ReservaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearReservaDepartamento_Exitosa() {
        User usuario = new User();
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(usuario));

        Departamento depto = new Departamento();
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(depto));

        ResponseEntity<?> response = reservaService.crearReservaDepartamento(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reserva generado exitosamente", response.getBody());

        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(reservaRepository).save(captor.capture());
        Reserva guardada = captor.getValue();

        assertAll("verificar campos de la reserva",
            () -> assertSame(usuario, guardada.getUser()),
            () -> assertSame(depto, guardada.getDepartamento()),
            () -> assertEquals(LocalDate.now(), guardada.getFecha_inicio()),
            () -> assertEquals(LocalDate.now().plusDays(2), guardada.getFecha_final()),
            () -> assertEquals(EstadoReserva.PENDIENTE, guardada.getEstadoReserva())
        );
    }

    @Test
    void crearReservaDepartamento_UsuarioNoEncontrado() {
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            reservaService.crearReservaDepartamento(1)
        );
        assertTrue(ex.getMessage().contains("usuario no encontrado"));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void crearReservaDepartamento_DepartamentoNoEncontrado() {
        User usuario = new User();
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(usuario));
        when(departamentoRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            reservaService.crearReservaDepartamento(999)
        );
        assertTrue(ex.getMessage().contains("departamento no encontrado"));
        verify(reservaRepository, never()).save(any());
    }
}
