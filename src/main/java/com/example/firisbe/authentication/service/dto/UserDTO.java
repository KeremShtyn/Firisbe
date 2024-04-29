package com.example.firisbe.authentication.service.dto;



import com.example.firisbe.authentication.entity.UserRole;
import com.example.firisbe.util.base.BaseDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String username;
    private UserRole role;
    private String email;

    private Set<UserTypeDTO> UserTypes;

}
