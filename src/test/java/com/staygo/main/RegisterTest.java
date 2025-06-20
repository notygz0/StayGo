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
class RegisterTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void registerUsuario() throws Exception {
        String username = "testuser_" + System.currentTimeMillis();

        mockMvc.perform(post("/auth/register")
                        .param("email", "sebastian@example.cl")
                        .param("username", username)
                        .param("password", "1234")
                        .param("confirmPassword", "1234"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String location = result.getResponse().getHeader("Location");
                    System.out.println("Status: " + status);
                    System.out.println("Location: " + location);
                    System.out.println("Content: " + result.getResponse().getContentAsString());
                    assertTrue(status >= 300 && status < 400, "Expected redirection status but got: " + status);
                    assertEquals("/login", location, "Expected redirection to /login but got: " + location);
                });
    }

}
