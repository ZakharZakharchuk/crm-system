package org.example.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Boolean isActive;

}
