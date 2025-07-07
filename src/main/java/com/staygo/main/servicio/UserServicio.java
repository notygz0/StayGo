package com.staygo.main.servicio;

import com.staygo.main.dto.UserResponse;
import com.staygo.main.entity.User;
import com.staygo.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserServicio {
    private final UserRepository userRepository;
    public void EstadoUsuario(Model model) {
        boolean isLoggedIn = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            model.addAttribute("isLoggedIn", isLoggedIn);
        }else {
            isLoggedIn=true;
            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("username", username);
        }
    }
    public ResponseEntity<?> obtenerInformacionUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UserResponse userResponseUser = UserResponse.builder()
                .username(user.getUsername())
                .correo(user.getCorreo())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .celular(user.getCelular())
                .build();
        return ResponseEntity.ok().body(userResponseUser);
    }
    public String FindRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().toString();
    }
}


