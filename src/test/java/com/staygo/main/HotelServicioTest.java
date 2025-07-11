package com.staygo.main;

import com.staygo.main.dto.HotelRequest;
import com.staygo.main.entity.Hotel;
import com.staygo.main.entity.User;
import com.staygo.main.entity.Role;
import com.staygo.main.repository.HotelRepository;
import com.staygo.main.repository.UserRepository;
import com.staygo.main.servicio.HotelServicio;
import com.staygo.main.servicio.ReservaService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServicioTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReservaService reservaService;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private HotelServicio hotelServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCrearHotel_exito() throws Exception {
        HotelRequest request = new HotelRequest();
        request.setNombre("Hotel Prueba");
        request.setDireccion("Calle 123");
        request.setDescripcion("Hotel bonito");
        request.setPrecio(1000.0F);
        request.setNumEstrellas(4);
        request.setNumHabitaciones(10);
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.isEmpty()).thenReturn(true);
        request.setImagen(imagenMock);
        User user = User.builder().username("admin").role(Role.ADMIN).password("1234").build();
        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(new Hotel());
        ResponseEntity<?> response = hotelServicio.crearhotel(request);
        assertEquals(200, response.getStatusCode().value());
        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void testCrearHotel_usuarioNoEncontrado() {
        HotelRequest request = new HotelRequest();
        when(authentication.getName()).thenReturn("noexiste");
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            hotelServicio.crearhotel(request);
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testCrearHotel_imagenInvalida() throws Exception {
        HotelRequest request = new HotelRequest();
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.isEmpty()).thenReturn(false);
        when(imagenMock.getBytes()).thenThrow(new java.io.IOException());
        request.setImagen(imagenMock);
        User user = User.builder().username("admin").role(Role.ADMIN).password("1234").build();
        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        ResponseEntity<?> response = hotelServicio.crearhotel(request);
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error al procesar la imagen", response.getBody());
    }

    @Test
    void testListarHotels_existen() {
        Hotel hotel = new Hotel();
        hotel.setNombre("Hotel Prueba");
        hotel.setDireccion("Calle 123");
        hotel.setDescripcion("Hotel bonito");
        hotel.setPrecio(1000.0F);
        hotel.setNumEstrellas(4);
        hotel.setNumHabitaciones(10);
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        ResponseEntity<?> response = hotelServicio.listarhotels();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testListarHotels_vacio() {
        when(hotelRepository.findAll()).thenReturn(List.of());
        ResponseEntity<?> response = hotelServicio.listarhotels();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testBorrarHotel_existe() {
        Hotel hotel = new Hotel();
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotel));
        ResponseEntity<?> response = hotelServicio.borrarHotel(1);
        verify(hotelRepository).delete(hotel);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testBorrarHotel_noExiste() {
        when(hotelRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            hotelServicio.borrarHotel(1);
        });
        assertEquals("hotel no encontrado", exception.getMessage());
    }
} 