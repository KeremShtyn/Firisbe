package com.example.firisbe.authentication.entity;


import com.example.firisbe.util.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@SQLDelete(sql = "UPDATE UserType SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_DATE is null")
@Entity(name = "UserType")
@Table(name = "UserType", indexes = {@Index(columnList = "working_id", name = "wrk_working_id_indx")})
@Data
@EqualsAndHashCode(callSuper = false)
@ToString( callSuper = true, includeFieldNames = true )
public class UserTypeEntity extends BaseEntity {

    @Column(name = "working_type")
    private String workingType;

    @Column(name = "working_id")
    private String workingId;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;
}
