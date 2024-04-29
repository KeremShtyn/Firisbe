package com.example.firisbe.authentication.mapper;


import com.example.firisbe.authentication.domain.UserType;
import com.example.firisbe.authentication.entity.UserTypeEntity;
import com.example.firisbe.util.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserTypeMapper extends BaseMapper<UserTypeEntity, UserType> {

    @Mapping(source = "userId", target = "user.id")
    UserTypeEntity toEntity(UserType domain);

    @Mapping(target = "userId", source = "user.id")
    UserType toDomainObject(UserTypeEntity entityObject);
}
