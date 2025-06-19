package com.staygo.main.servicio;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserServicio {
     public void EstadoUsuario(Model model) {
        boolean isLoggedIn = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Usuario autenticado: " + username);
        System.out.println("Estado de autenticación: " + authentication.isAuthenticated());
        System.out.println("isloggedIn: " + isLoggedIn);
        if (username.equals("anonymousUser")) {
            model.addAttribute("isLoggedIn", isLoggedIn);
        }else {
            isLoggedIn=true;
            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("username", username);
        }
    }
}
