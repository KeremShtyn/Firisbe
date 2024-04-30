package com.example.firisbe.payment.repository;

import com.example.firisbe.payment.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String>, JpaSpecificationExecutor<PaymentEntity> {

    List<PaymentEntity> findByUserId(String userId);

    Page<PaymentEntity> findByCreatedDateBetween(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    Page<PaymentEntity> findPageByUserId(Pageable pageable, String userId);
}
