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
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void crearReservaDepartamento_Exitosa() {
        when(authentication.getName()).thenReturn("testUser");
        User usuario = new User();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usuario));

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
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reservaService.crearReservaDepartamento(1)
        );

        assertEquals("Usuario no encontrado con el nombre: testUser", ex.getMessage());

        verify(reservaRepository, never()).save(any());
    }

    @Test
    void crearReservaDepartamento_DepartamentoNoEncontrado() {
        when(authentication.getName()).thenReturn("testUser");
        User usuario = new User();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usuario));
        when(departamentoRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reservaService.crearReservaDepartamento(999)
        );

        assertEquals("Departamento no encontrado con id: 999", ex.getMessage());

        verify(reservaRepository, never()).save(any());
    }


}
