package com.example.firisbe.authentication.entity;


import com.example.firisbe.payment.entity.PaymentEntity;
import com.example.firisbe.util.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;

@SQLDelete(sql = "UPDATE Users SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_DATE is null")
@Entity(name = "Users")
@Table(name = "Users", indexes = {@Index(columnList = "USERNAME", name = "user_username_indx")})
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"UserTypes"})
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "CARD_NUMBER")
    private String creditCardNumber;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<PaymentEntity> payments;

    private Boolean locked = false;

    private Boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<UserTypeEntity> UserTypes;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(this.UserTypes)) {
            SimpleGrantedAuthority simpleGrantedAuthority = null;
            if ("ADMIN".equals(this.role.name())) {
                simpleGrantedAuthority = new SimpleGrantedAuthority("ADMIN");
            } else {
                simpleGrantedAuthority = new SimpleGrantedAuthority("UNKNOWN");
            }
            return Collections.singletonList(simpleGrantedAuthority);
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        this.UserTypes.forEach(f -> {
            final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(f.getRole().name());
            simpleGrantedAuthorities.add(simpleGrantedAuthority);
        });

        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(getRole().name());
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}
