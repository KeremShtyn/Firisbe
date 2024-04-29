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
@Table(name = "Payments", indexes = {@Index(columnList = "USERNAME", name = "payment_username_indx")})
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"UserTypes"})
public class PaymentEntity extends BaseEntity {



    @Column(name = "CARD_NUMBER")
    private String creditCardNumber;

    @Column(name = "AMOUNT")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;


}
