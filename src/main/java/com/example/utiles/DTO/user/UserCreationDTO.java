package com.example.utiles.DTO.user;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDTO {
    private String name;
    private String email;
    private String password;
    private Long roleId;
}
