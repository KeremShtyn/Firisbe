package com.example.firisbe.authentication.dto;


import com.example.firisbe.authentication.entity.UserRole;
import com.example.firisbe.util.base.BaseDTO;
import lombok.Data;

@Data
public class UserTypeDTO extends BaseDTO {

    private String workingType;
    private String workingId;
    private UserRole role;
    private String userId;
}
