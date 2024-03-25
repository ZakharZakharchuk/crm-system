package org.example.data.dto;

import lombok.Data;
import org.example.data.entity.Role;

@Data
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
