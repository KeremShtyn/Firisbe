package com.example.firisbe.payment.service;

import com.example.firisbe.authentication.entity.UserEntity;
import com.example.firisbe.authentication.repository.UserRepository;
import com.example.firisbe.payment.domain.Payment;
import com.example.firisbe.payment.entity.PaymentEntity;
import com.example.firisbe.payment.mapper.PaymentMapper;
import com.example.firisbe.payment.repository.PaymentRepository;
import com.example.firisbe.util.FirisbePageable;
import com.example.firisbe.util.error.ErrorCodes;
import com.example.firisbe.util.exception.FirisbeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    private PaymentMapper paymentMapper;

    private UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userRepository = userRepository;
    }


    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public Payment createPayment(Payment payment, String userId){
        this.validatePayment(payment, userId);
        return save(payment, userId);
    }

    private Payment save(Payment payment, String userId){
        PaymentEntity save = paymentRepository.save(paymentMapper.toEntity(payment));
        Optional<UserEntity> user = userRepository.findById(userId);
        user.get().getPayments().add(save);
        return paymentMapper.toDomainObject(save);
    }

    private void validatePayment(Payment payment, String userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new FirisbeException(ErrorCodes.THIS_USER_DOES_NOT_EXIST);
        }
        if (Objects.isNull(payment.getAmount())){
            throw new FirisbeException(ErrorCodes.PAYMENT_AMOUNT_CAN_NOT_BE_EMPTY);
        }
        if (Objects.isNull(payment.getUserCreditCardNumber())){
            throw new FirisbeException(ErrorCodes.CARD_NUMBER_CAN_NOT_BE_EMPTY);
        }

    }

    public List<Payment> getPaymentsByUser(String userId){
        List<PaymentEntity> paymentList = paymentRepository.findByUserId(userId);
        return paymentMapper.toListDomainObject(paymentList);
    }

    public FirisbePageable<Payment> getPaymentsByCreatedDate(int page, int size, LocalDateTime startDate, LocalDateTime endDate){
        Page<PaymentEntity> payments = paymentRepository.findByCreatedDateBetween(PageRequest.of(page, size), startDate, endDate);
         return new FirisbePageable<Payment>(payments.getTotalElements(), payments.getTotalPages(), payments.getPageable(), paymentMapper.toListDomainObject(payments.getContent()));
    }

    public FirisbePageable<Payment> findPageByUser(int page, int size, String userId){
        Page<PaymentEntity> payments = paymentRepository.findPageByUserId(PageRequest.of(page, size), userId);
        return new FirisbePageable<Payment>(payments.getTotalElements(),payments.getTotalPages(),payments.getPageable(),paymentMapper.toListDomainObject(payments.getContent()));
    }

    @Transactional
    public FirisbePageable<Payment> findBySearchCriteria(Specification<PaymentEntity> spec, Pageable page){
        Page<PaymentEntity> payments = paymentRepository.findAll(spec, page);
        return new FirisbePageable<Payment>(payments.getTotalElements(),payments.getTotalPages(),payments.getPageable(),paymentMapper.toListDomainObject(payments.getContent()));
    }

}
