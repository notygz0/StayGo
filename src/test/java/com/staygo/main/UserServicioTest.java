package com.staygo.main;

import com.staygo.main.entity.User;
import com.staygo.main.repository.UserRepository;
import com.staygo.main.servicio.UserServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicioTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private UserServicio userServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testEstadoUsuario_autenticado() {
        when(authentication.getName()).thenReturn("usuario");
        var model = new ConcurrentModel();
        userServicio.EstadoUsuario(model);
        assertTrue((Boolean) model.getAttribute("isLoggedIn"));
        assertEquals("usuario", model.getAttribute("username"));
    }

    @Test
    void testEstadoUsuario_anonimo() {
        when(authentication.getName()).thenReturn("anonymousUser");
        var model = new ConcurrentModel();
        userServicio.EstadoUsuario(model);
        assertFalse((Boolean) model.getAttribute("isLoggedIn"));
        assertNull(model.getAttribute("username"));
    }

    @Test
    void testObtenerInformacionUsuario_encontrado() {
        when(authentication.getName()).thenReturn("usuario");
        User user = new User();
        user.setUsername("usuario");
        user.setCorreo("usuario@correo.com");
        user.setFirstname("Nombre");
        user.setLastname("Apellido");
        user.setCelular("123456789");
        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        ResponseEntity<?> response = userServicio.obtenerInformacionUsuario();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testObtenerInformacionUsuario_noEncontrado() {
        when(authentication.getName()).thenReturn("noexiste");
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userServicio.obtenerInformacionUsuario();
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testFindRole_autenticado() {
        var auth = new UsernamePasswordAuthenticationToken(
            "usuario",
            "1234",
            java.util.List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String roles = userServicio.FindRole();
        System.out.println("Roles autenticado: " + roles); 
        assertEquals("[ROLE_USER]", roles);
    }

    @Test
    void testFindRole_anonimo() {
        var auth = new UsernamePasswordAuthenticationToken("anonymousUser", null, java.util.List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String roles = userServicio.FindRole();
        System.out.println("Roles anónimo: " + roles);
        assertEquals("[]", roles);
    }
}