package com.example.firisbe.authentication.domain;


import com.example.firisbe.authentication.entity.UserRole;
import com.example.firisbe.payment.entity.PaymentEntity;
import com.example.firisbe.util.base.BaseDomain;
import lombok.Data;

import java.util.Set;

@Data
public class User extends BaseDomain {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private UserRole role;
    private String creditCardNumber;

    private Set<UserType> UserTypes;
    private String paymentId;

}
