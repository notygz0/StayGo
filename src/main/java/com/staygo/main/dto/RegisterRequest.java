package com.staygo.main.dto;

import com.staygo.main.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String celular;
    private String password;
    private String confirmPassword;
    private Role role;
}
