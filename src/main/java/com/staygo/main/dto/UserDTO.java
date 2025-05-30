package com.staygo.main.dto;

import com.staygo.main.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String password;  // puede haber problema de seguridad aca, lo dejare como placeholder
    private String firstname;
    private String lastname;
    private String celular;
    private String correo;
    private Role role;
}

