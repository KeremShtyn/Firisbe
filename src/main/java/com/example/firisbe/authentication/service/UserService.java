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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private UserMapper userMapper;

    private UserRepository userRepository;

    private static final String SECRET_KEY = "Th3f!R!sB3s3cr3tK3y";

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
        users.getContent().stream().map(user -> {
            String decryptedCreditCardNumbers = decrypt(user.getCreditCardNumber());
            user.setCreditCardNumber(decryptedCreditCardNumbers);
            return user;
        }).collect(Collectors.toList());

        return new FirisbePageable<User>(users.getTotalElements(),users.getTotalPages(),users.getPageable(),userMapper.toListDomainObject(users.getContent()));
    }

    public User saveOrUpdate(User user) {
        this.uniquenessUser(user);
        UserEntity userEntity = userMapper.toEntity(user);
        if (!CollectionUtils.isEmpty(userEntity.getUserTypes())) {
            userEntity.getUserTypes().forEach(u -> u.setUser(userEntity));
        }
        String encryptedCreditCardNumber = encrypt(userEntity.getCreditCardNumber());
        userEntity.setCreditCardNumber(encryptedCreditCardNumber);
        return userMapper.toDomainObject(userRepository.save(userEntity));
    }



    private void uniquenessUser(User user) {
        Optional<UserEntity> usernameOpt = userRepository.findByUsername(user.getUsername());
        if (usernameOpt.isPresent()) {
            throw new FirisbeException(ErrorCodes.THIS_USERNAME_HAS_TAKEN_BEFORE);
        }
        Optional<UserEntity> emailOpt = userRepository.findByEmail(user.getEmail());
        if (emailOpt.isPresent()) {
            throw new FirisbeException(ErrorCodes.THIS_EMAIL_HAS_TAKEN_BEFORE);
        }
        Optional<UserEntity> creditCardOpt = userRepository.findByCreditCardNumber(user.getCreditCardNumber());
        if (creditCardOpt.isPresent()) {
            throw new FirisbeException(ErrorCodes.THIS_CREDIT_CARD_HAS_TAKEN_BEFORE);
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.out.println("Encryption failed: " + e.getMessage());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Decryption fail: " + e.getMessage());
        }
        return null;
    }
}
