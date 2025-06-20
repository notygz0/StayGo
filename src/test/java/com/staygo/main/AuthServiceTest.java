package com.staygo.main;

import com.staygo.main.dto.RegisterRequest;
import com.staygo.main.entity.User;
import com.staygo.main.entity.Role;
import com.staygo.main.repository.UserRepository;
import com.staygo.main.servicio.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void inicializarMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registroExitosoDevuelveTrueYGuardaUsuario() {
        RegisterRequest solicitud = new RegisterRequest();
        solicitud.setUsername("cuervas");
        solicitud.setEmail("cuervas@staygo.cl");
        solicitud.setPassword("Pass1234");
        solicitud.setConfirmPassword("Pass1234");

        when(userRepository.findByUsername("cuervas")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Pass1234")).thenReturn("hashPass1234");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        boolean resultado = authService.register(solicitud);

        assertTrue(resultado, "debe retornar true cuando el registro es exitoso");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User usuarioGuardado = captor.getValue();

        assertAll("verificar campos del usuario guardado",
            () -> assertEquals("cuervas", usuarioGuardado.getUsername()),
            () -> assertEquals("cuervas@staygo.cl", usuarioGuardado.getCorreo()),
            () -> assertEquals("hashPass1234", usuarioGuardado.getPassword()),
            () -> assertEquals(Role.USER, usuarioGuardado.getRole())
        );
    }

    @Test
    void registroFallaCuandoUsuarioExiste() {
        RegisterRequest solicitud = new RegisterRequest();
        solicitud.setUsername("usuarioExistente");
        solicitud.setEmail("existente@gmail.com");
        solicitud.setPassword("1234");
        solicitud.setConfirmPassword("1234");

        when(userRepository.findByUsername("usuarioExistente"))
            .thenReturn(Optional.of(new User()));

        boolean resultado = authService.register(solicitud);

        assertFalse(resultado, "debe retornar false cuando el usuario ya existe");
        verify(userRepository, never()).save(any());
    }

}
