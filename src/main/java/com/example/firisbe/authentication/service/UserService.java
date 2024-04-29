package com.example.firisbe.authentication.service;


import com.example.firisbe.authentication.domain.User;
import com.example.firisbe.authentication.entity.UserEntity;
import com.example.firisbe.util.error.ErrorCodes;
import com.example.firisbe.authentication.mapper.UserMapper;
import com.example.firisbe.authentication.repository.UserRepository;
import com.example.firisbe.util.FirisbePageable;
import com.example.firisbe.util.exception.FirisbeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private UserMapper userMapper;

    private UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDomainObject).orElseThrow(() -> new FirisbeException(ErrorCodes.THIS_USER_DOES_NOT_EXIST));
    }

    public User findOne(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDomainObject).orElseThrow(() -> new FirisbeException(ErrorCodes.ACCESS_DENIED));
    }

    public FirisbePageable<User> findAll(int page, int size){
        Page<UserEntity> users = userRepository.findAll(PageRequest.of(page, size));
        return new FirisbePageable<User>(users.getTotalElements(),users.getTotalPages(),users.getPageable(),userMapper.toListDomainObject(users.getContent()));
    }

    public User saveOrUpdate(User user) {
        this.uniquenessUser(user.getUsername());
        UserEntity userEntity = userMapper.toEntity(user);
        if (!CollectionUtils.isEmpty(userEntity.getUserTypes())) {
            userEntity.getUserTypes().forEach(u -> u.setUser(userEntity));
        }

        return userMapper.toDomainObject(userRepository.save(userEntity));
    }



    private void uniquenessUser(String username) {
        Optional<UserEntity> usernameOpt = userRepository.findByUsername(username);
        if (usernameOpt.isPresent()) {
            throw new FirisbeException(ErrorCodes.THIS_USERNAME_HAS_TAKEN_BEFORE);

        }
    }
}
