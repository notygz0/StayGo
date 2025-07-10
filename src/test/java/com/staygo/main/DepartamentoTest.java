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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

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
    @Mock
    private com.staygo.main.servicio.ReservaService reservaService;

    @InjectMocks
    private DepartamentoServicio departamentoServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCrearDepartamento() throws Exception {
        DepartamentoRequest request = new DepartamentoRequest();
        request.setNombre("Departamento");
        request.setDescripcion("Bonito depto");
        request.setPrecio(1000.0F);
        request.setNumHabitaciones(2);

        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.isEmpty()).thenReturn(true);
        request.setImagen(imagenMock);

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

        assertEquals(200, response.getStatusCode().value());
        verify(departamentoRepository).save(any(Departamento.class));
    }

    @Test
    void testCrearDepartamento_usuarioNoEncontrado() {
        DepartamentoRequest request = new DepartamentoRequest();
        when(authentication.getName()).thenReturn("noexiste");
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departamentoServicio.crearDepartamento(request);
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testCrearDepartamento_imagenInvalida() throws Exception {
        DepartamentoRequest request = new DepartamentoRequest();
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.isEmpty()).thenReturn(false);
        when(imagenMock.getBytes()).thenThrow(new java.io.IOException());
        request.setImagen(imagenMock);
        User user = new User();
        when(authentication.getName()).thenReturn("Cuervas");
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(user));
        ResponseEntity<?> response = departamentoServicio.crearDepartamento(request);
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error al procesar la imagen", response.getBody());
    }

    @Test
    void testCrearDepartamento_imagenNulaOVacia() {
        DepartamentoRequest request = new DepartamentoRequest();
        request.setImagen(null);
        User user = new User();
        when(authentication.getName()).thenReturn("Cuervas");
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(user));
        when(departamentoRepository.save(any(Departamento.class))).thenReturn(new Departamento());
        ResponseEntity<?> response = departamentoServicio.crearDepartamento(request);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testListarDepartamentosDeUsuario_usuarioConDepartamentos() {
        User user = new User();
        Departamento dep = new Departamento();
        dep.setDueno(user);
        user.setDepartamentos(java.util.List.of(dep));
        when(authentication.getName()).thenReturn("Cuervas");
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(user));
        ResponseEntity<?> response = departamentoServicio.listarDepartamentosDeUsuario();
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testListarDepartamentosDeUsuario_usuarioSinDepartamentos() {
        User user = new User();
        user.setDepartamentos(java.util.List.of());
        when(authentication.getName()).thenReturn("Cuervas");
        when(userRepository.findByUsername("Cuervas")).thenReturn(Optional.of(user));
        ResponseEntity<?> response = departamentoServicio.listarDepartamentosDeUsuario();
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testListarDepartamentosDeUsuario_usuarioNoEncontrado() {
        when(authentication.getName()).thenReturn("noexiste");
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departamentoServicio.listarDepartamentosDeUsuario();
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testListarDepartamentos_existenDepartamentos() {
        Departamento dep = new Departamento();
        User dueno = new User();
        dueno.setUsername("usuario");
        dep.setDueno(dueno);
        when(departamentoRepository.findAll()).thenReturn(java.util.List.of(dep));
        ResponseEntity<?> response = departamentoServicio.listarDepartamentos();
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testListarDepartamentos_noExistenDepartamentos() {
        when(departamentoRepository.findAll()).thenReturn(java.util.List.of());
        ResponseEntity<?> response = departamentoServicio.listarDepartamentos();
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testBorrarDepartamento_existeSinReservas() {
        Departamento dep = new Departamento();
        dep.setReservas(java.util.List.of());
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(dep));
        ResponseEntity<?> response = departamentoServicio.borrarDepartamento(1);
        verify(departamentoRepository).delete(dep);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testBorrarDepartamento_existeConReservas() {
        Departamento dep = new Departamento();
        dep.setReservas(java.util.List.of(new com.staygo.main.entity.Reserva()));
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(dep));
        ResponseEntity<?> response = departamentoServicio.borrarDepartamento(1);
        verify(reservaService).borrarReservaPorDepartamento(1);
        verify(departamentoRepository).delete(dep);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testBorrarDepartamento_noExiste() {
        when(departamentoRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departamentoServicio.borrarDepartamento(1);
        });
        assertEquals("Departamento no encontrado", exception.getMessage());
    }
}