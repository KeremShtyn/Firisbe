package com.example.firisbe.payment.mapper;

import com.example.firisbe.payment.domain.Payment;
import com.example.firisbe.payment.dto.PaymentDTO;
import com.example.firisbe.util.base.BaseDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDTOMapper extends BaseDTOMapper<Payment, PaymentDTO> {
}
