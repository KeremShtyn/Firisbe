package com.example.firisbe.authentication.controller;


import com.example.firisbe.authentication.api.AuthAPI;
import com.example.firisbe.authentication.domain.User;
import com.example.firisbe.authentication.domain.UserType;
import com.example.firisbe.authentication.dto.TokenRequestDTO;
import com.example.firisbe.authentication.dto.TokenResponseDTO;
import com.example.firisbe.authentication.dto.UserDTO;
import com.example.firisbe.authentication.dto.UserDataDTO;
import com.example.firisbe.authentication.mapper.UserDTOMapper;
import com.example.firisbe.authentication.mapper.UserDataDTOMapper;
import com.example.firisbe.authentication.service.UserService;
import com.example.firisbe.authentication.service.UserTypeService;
import com.example.firisbe.security.JwtTokenUtil;
import com.example.firisbe.util.FirisbePageable;
import com.example.firisbe.util.response.FirisbeGenerator;
import com.example.firisbe.util.response.FirisbeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
public class AuthController implements AuthAPI {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDTOMapper userDTOMapper;

    private final UserTypeService UserTypeService;

    private final UserDataDTOMapper userDataDTOMapper;


    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtTokenUtil jwtTokenUtil, UserDTOMapper userDTOMapper, UserTypeService UserTypeService, UserDataDTOMapper userDataDTOMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDTOMapper = userDTOMapper;
        this.UserTypeService = UserTypeService;
        this.userDataDTOMapper = userDataDTOMapper;
    }

    @Override
    public FirisbeResponse<Object> token(TokenRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final User user = userService.findOne(request.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        final UserDataDTO userData = userDataDTOMapper.toDTO(user);
        TokenResponseDTO response = new TokenResponseDTO();
        response.setAccessToken(token);
        response.setUserData(userData);


        return FirisbeGenerator.generateSignResponse(response);
    }

    @Override
    public FirisbeResponse<Object> saveUser(UserDTO userDTO) {

        User domainObject = userDTOMapper.toDomain(userDTO);
        domainObject.setPassword(new BCryptPasswordEncoder().encode(domainObject.getPassword()));
        UserType UserTypeObject = new UserType();
        UserTypeObject.setRole(domainObject.getRole());
        UserTypeObject.setWorkingType(domainObject.getRole().toString());
        UserTypeObject.setUserId(domainObject.getId());
        domainObject.setUserTypes(Set.of(UserTypeObject));
        domainObject = userService.saveOrUpdate(domainObject);
        UserTypeObject = UserTypeService.findOne(domainObject.getUserTypes().stream().findFirst().get().getId());
        UserTypeObject.setWorkingId(domainObject.getId());
        UserTypeService.saveOrUpdate(UserTypeObject);

        return FirisbeGenerator.generateSignResponse(userDTOMapper.toDTO(userService.findOne(domainObject.getUsername())));
    }

    @Override
    public FirisbeResponse<Object> update(UserDTO userDTO) {
        return saveUser(userDTO);
    }

    @Override
    public FirisbeResponse<Object> getByUsername(String username) {
        User user = userService.findByUsername(username);
        return FirisbeGenerator.generateSignResponse(userDTOMapper.toDTO(user));
    }

    @Override
    public FirisbeResponse<Object> getPage(Map<String, Object> header, int page, int size, String[] sortBy) {
        FirisbePageable<User> userPage = userService.findAll(page, size);
        return FirisbeGenerator.generateSignResponse(userPage);
    }

}
