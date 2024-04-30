package com.example.firisbe.payment.dto;

import com.example.firisbe.util.base.BaseDTO;
import lombok.Data;

@Data
public class PaymentDTO extends BaseDTO {

    private double amount;

    private String userId;

    private String userEmail;
}
