package com.example.firisbe.authentication.repository;


import com.example.firisbe.authentication.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, String> {

}
