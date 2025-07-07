package com.staygo.main;

import com.staygo.main.dto.ReservaRequest;
import com.staygo.main.entity.Departamento;

import com.staygo.main.entity.Reserva;
import com.staygo.main.entity.User;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void crearReservaDepartamentoCorrecto() {
        when(authentication.getName()).thenReturn("usuarioTest");
        User user = new User();
        when(userRepository.findByUsername("usuarioTest")).thenReturn(Optional.of(user));

        Departamento departamento = new Departamento();
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(departamento));

        ReservaRequest request = new ReservaRequest();
        request.setIdAlojamiento(1);
        request.setFechaInicio(LocalDate.now());
        request.setFechaFin(LocalDate.now().plusDays(2));


        ResponseEntity<?> response = reservaService.crearReservaDepartamento(request);

        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(Reserva.class, response.getBody());
        verify(reservaRepository).save(any(Reserva.class));
    }
}
