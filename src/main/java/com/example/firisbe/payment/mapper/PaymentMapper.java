package com.example.firisbe.payment.mapper;

import com.example.firisbe.payment.domain.Payment;
import com.example.firisbe.payment.entity.PaymentEntity;
import com.example.firisbe.util.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<PaymentEntity, Payment> {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "userEmail", target = "user.email")
    PaymentEntity toEntity(Payment domain);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    Payment toDomainObject(PaymentEntity entityObject);

}
