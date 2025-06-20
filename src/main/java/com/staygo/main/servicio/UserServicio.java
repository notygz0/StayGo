package com.staygo.main.servicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Slf4j
@Service
public class UserServicio {

    public void estadoUsuario(Model model) {
        boolean isLoggedIn = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Usuario autenticado: {}", username);
        log.info("Estado de autenticación: {}", authentication.isAuthenticated());
        log.info("isLoggedIn: {}", isLoggedIn);

        if ("anonymousUser".equals(username)) {
            model.addAttribute("isLoggedIn", isLoggedIn);
        } else {
            isLoggedIn = true;
            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("username", username);
        }
    }
}


