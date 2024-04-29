package com.example.firisbe.authentication.mapper;


import com.example.firisbe.authentication.domain.User;
import com.example.firisbe.authentication.entity.UserEntity;
import com.example.firisbe.util.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserTypeMapper.class})
public interface UserMapper extends BaseMapper<UserEntity, User> {
}
