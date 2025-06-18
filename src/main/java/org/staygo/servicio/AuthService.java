package org.staygo.servicio;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.staygo.dto.RegisterRequest;
import org.staygo.entity.Role;
import org.staygo.entity.User;
import org.staygo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public boolean login(String username, String password, HttpServletRequest request) {
        System.out.println("Attempting to authenticate user: " + username);
        System.out.println("Password provided: " + password);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession(true)
                   .setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return false;
        }
        if (registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            User user = User.builder()
                            .username(registerRequest.getUsername())
                            .correo(registerRequest.getEmail())
                            .password(passwordEncoder.encode(registerRequest.getPassword()))
                            .role(Role.USER)
                            .build();
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
