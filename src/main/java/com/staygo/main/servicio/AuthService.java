package com.staygo.main.servicio;

import com.staygo.main.dto.RegisterRequest;
import com.staygo.main.entity.User;
import com.staygo.main.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public boolean login(String username, String password, HttpServletRequest request) {
        log.info("Intentando autenticar usuario: {}", username);
        log.debug("Password proporcionada" ); //password no especificada por seguridad

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            log.info("Autenticación exitosa para el usuario: {}", username);
            return true;
        } catch (Exception e) {
            log.warn("Fallo de autenticación para el usuario {}: {}", username, e.getMessage());
            return false;
        }
    }

    public boolean register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            return false;
        }
        if (registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            User user = User.builder()
                    .username(registerRequest.getUsername())
                    .firstname(registerRequest.getFirstname())
                    .lastname(registerRequest.getLastname())
                    .celular(registerRequest.getCelular())
                    .correo(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getRole())
                    .build();
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }
}

