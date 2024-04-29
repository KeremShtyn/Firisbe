package com.example.firisbe.authentication.service.dto;


import com.example.firisbe.authentication.entity.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UserDataDTO {

    private String firstName;
    private String lastName;
    private String username;
    private UserRole role;
    private String email;
    private Set<UserTypeDTO> UserTypes;
}
