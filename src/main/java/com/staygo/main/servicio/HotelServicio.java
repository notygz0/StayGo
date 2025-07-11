package com.staygo.main.servicio;

import com.staygo.main.dto.HotelRequest;
import com.staygo.main.dto.HotelResponse;
import com.staygo.main.entity.Hotel;
import com.staygo.main.entity.User;
import com.staygo.main.repository.HotelRepository;
import com.staygo.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServicio {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final ReservaService reservaService;

    public ResponseEntity<?> crearhotel(HotelRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User dueno = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Hotel hotel = Hotel.builder()
                .nombre(request.getNombre())
                .direccion(request.getDireccion())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .numEstrellas(request.getNumEstrellas())
                .numHabitaciones(request.getNumHabitaciones())
                .build();
        if (request.getImagen() != null && !request.getImagen().isEmpty()) {
            try {
                hotel.setImagen(request.getImagen().getBytes());
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Error al procesar la imagen");
            }
        }
        hotelRepository.save(hotel);
        return ResponseEntity.ok("hotel creado exitosamente");
    }
    public ResponseEntity<?> listarhotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return getListhotel(hotels);
    }

    private ResponseEntity<?> getListhotel(List<Hotel> hotels) {
        List<HotelResponse> response = hotels.stream()
                .map(hotel -> HotelResponse.builder()
                        .id(hotel.getId())
                        .nombre(hotel.getNombre())
                        .direccion(hotel.getDireccion())
                        .descripcion(hotel.getDescripcion())
                        .precio(hotel.getPrecio())
                        .numHabitaciones(hotel.getNumHabitaciones())
                        .imagen(hotel.getImagen() != null ?
                                Base64.getEncoder().encodeToString(hotel.getImagen()) : null)
                        .build())
                .toList();
        return ResponseEntity.ok().body(response);
    }
    public ResponseEntity<?> borrarHotel(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("hotel no encontrado"));
        hotelRepository.delete(hotel);
        return ResponseEntity.ok("hotel eliminado exitosamente");
    }
}