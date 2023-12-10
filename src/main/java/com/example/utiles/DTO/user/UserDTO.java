package com.example.utiles.DTO.user;

import com.example.role.Role;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long role_id;
    private Role role;

}
