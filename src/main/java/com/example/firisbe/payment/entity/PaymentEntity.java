package com.example.firisbe.payment.entity;

import com.example.firisbe.authentication.entity.UserEntity;
import com.example.firisbe.util.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@SQLDelete(sql = "UPDATE Payments SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_DATE is null")
@Entity(name = "Payments")
@Table(name = "Payments", indexes = {@Index(columnList = "CARD_NUMBER", name = "payment_card_number_indx")})
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"USER_ID"})
public class PaymentEntity extends BaseEntity {


    @Column(name = "AMOUNT")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;


}
