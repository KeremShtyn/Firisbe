package com.example.firisbe.authentication.domain;


import com.example.firisbe.authentication.entity.UserRole;
import com.example.firisbe.util.base.BaseDomain;
import lombok.Data;

@Data
public class UserType extends BaseDomain {

    private String workingType;
    private String workingId;
    private UserRole role;
    private String userId;

}
