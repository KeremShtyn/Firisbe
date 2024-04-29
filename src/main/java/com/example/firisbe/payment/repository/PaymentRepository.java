package com.example.firisbe.payment.repository;

import com.example.firisbe.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    List<PaymentEntity> findByUserId(String userId);

    List<PaymentEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
