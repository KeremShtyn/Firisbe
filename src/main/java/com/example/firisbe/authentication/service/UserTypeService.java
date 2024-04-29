package com.example.firisbe.authentication.service;


import com.example.firisbe.authentication.domain.UserType;
import com.example.firisbe.util.error.ErrorCodes;
import com.example.firisbe.authentication.mapper.UserTypeMapper;
import com.example.firisbe.authentication.repository.UserTypeRepository;
import com.example.firisbe.util.exception.FirisbeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserTypeService {

    private UserTypeMapper userTypeMapper;

    private UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeMapper userTypeMapper, UserTypeRepository userTypeRepository){
        this.userTypeMapper = userTypeMapper;
        this.userTypeRepository = userTypeRepository;
    }

    public UserType findOne(String id) {
        return userTypeRepository.findById(id).map(userTypeMapper::toDomainObject).orElseThrow(() -> new FirisbeException(ErrorCodes.THIS_USER_DOES_NOT_EXIST));
    }

    public UserType saveOrUpdate(UserType UserType) {
        return userTypeMapper.toDomainObject(userTypeRepository.save(userTypeMapper.toEntity(UserType)));
    }
}
