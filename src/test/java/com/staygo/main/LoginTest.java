package com.staygo.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;

import com.staygo.main.servicio.UserServicio;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class LoginTest {
    private final String username = "Cuervas";
    private final String password = "1234";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServicio userServicio;

    @Test
    void loginUsuario_activo() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertEquals(200, status);
                });
    }

    @Test
    void estadoUsuario() {
        var auth = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var model = new ConcurrentModel();
        userServicio.EstadoUsuario(model);

        Object isLoggedIn = model.getAttribute("isLoggedIn");
        Object retrievedUsername = model.getAttribute("username");

        assertNotNull(isLoggedIn, "isLoggedIn attribute should not be null");
        assertTrue(isLoggedIn instanceof Boolean && (Boolean) isLoggedIn, "isLoggedIn should be true");
        assertNotNull(retrievedUsername, "username attribute should not be null");
        assertEquals(username, retrievedUsername, "username should match the expected value");
    }
}