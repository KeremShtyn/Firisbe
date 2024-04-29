package com.example.firisbe.payment.domain;

import com.example.firisbe.util.base.BaseDomain;
import lombok.Data;

@Data
public class Payment extends BaseDomain {

    private String creditCardNumber;

    private double amount;

    private String userId;

    private String username;

    private String userEmail;
}
