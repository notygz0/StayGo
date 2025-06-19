package com.staygo.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void registerUsuario() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("email", "sebastian@example.cl")
                        .param("username", "prueba")
                        .param("password", "1234")
                        .param("confirmPassword", "1234"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String location = result.getResponse().getHeader("Location");
                    System.out.println("Status: " + status);
                    System.out.println("Location: " + location);
                    assertTrue(status >= 300 && status < 400, "Expected redirection status but got: " + status);
                    assertEquals("/login", location, "Expected redirection to /login but got: " + location);
                });

    }
}
