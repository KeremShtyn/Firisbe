package com.example.firisbe.payment.service;

import com.example.firisbe.payment.domain.Payment;
import com.example.firisbe.payment.entity.PaymentEntity;
import com.example.firisbe.payment.mapper.PaymentMapper;
import com.example.firisbe.payment.repository.PaymentRepository;
import com.example.firisbe.util.FirisbePageable;
import com.example.firisbe.util.error.ErrorCodes;
import com.example.firisbe.util.exception.ErrorCode;
import com.example.firisbe.util.exception.FirisbeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    private PaymentMapper paymentMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    private Payment createPayment(Payment payment){
        this.validatePayment(payment);
        return save(payment);
    }

    private Payment save(Payment payment){
        return paymentMapper.toDomainObject(paymentRepository.save(paymentMapper.toEntity(payment)));
    }

    private void validatePayment(Payment payment){
        if (Objects.isNull(payment.getAmount())){
            throw new FirisbeException(ErrorCodes.PAYMENT_AMOUNT_CAN_NOT_BE_EMPTY);
        }
        if (Objects.isNull(payment.getCreditCardNumber())){
            throw new FirisbeException(ErrorCodes.CARD_NUMBER_CAN_NOT_BE_EMPTY);
        }

    }

    private List<Payment> getPaymentsByUser(String userId){
        List<PaymentEntity> paymentList = paymentRepository.findByUserId(userId);
        return paymentMapper.toListDomainObject(paymentList);
    }

    private List<Payment> getPaymentsByCreatedDate(LocalDateTime startDate, LocalDateTime endDate){
        return 
    }

}
