package com.staygo.main;

import com.staygo.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }
    @Test
    void registerUsuario() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("email", "sebastian@example.cl")
                        .param("username", "prueba")
                        .param("password", "1234")
                        .param("confirmPassword", "1234")
                        .param("firstname", "Sebastián")
                        .param("lastname", "Prueba")
                        .param("celular", "123456789")
                        .param("role", "USER"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String location = result.getResponse().getHeader("Location");
                    String content = result.getResponse().getContentAsString();
                    System.out.println("Status: " + status);
                    System.out.println("Location: " + location);
                    System.out.println("Content: " + content);
                    assertTrue(status >= 300 && status < 400 && "/login".equals(location));
                });
    }

    @Test
    void registerUsuario_usernameExistente() throws Exception {
        // primero registrar el usuario
        mockMvc.perform(post("/auth/register")
                        .param("email", "sebastian@example.cl")
                        .param("username", "prueba_existente")
                        .param("password", "1234")
                        .param("confirmPassword", "1234"));
        // intenta registrar el mismo usuario otra vez
        mockMvc.perform(post("/auth/register")
                        .param("email", "sebastian@example.cl")
                        .param("username", "prueba_existente")
                        .param("password", "1234")
                        .param("confirmPassword", "1234"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String error = result.getResponse().getContentAsString();
                    assertTrue(status == 200 || status == 400);
                    assertTrue(error.contains("Registro fallido") || error.contains("ya hay una cuenta existente"));
                });
    }

    @Test
    void registerUsuario_contrasenasNoCoinciden() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("email", "sebastian@example.cl")
                        .param("username", "prueba_no_coincide")
                        .param("password", "1234")
                        .param("confirmPassword", "5678"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String error = result.getResponse().getContentAsString();
                    assertTrue(status == 200 || status == 400);
                    assertTrue(error.contains("Registro fallido") || error.contains("verifica tus datos"));
                });
    }
}
