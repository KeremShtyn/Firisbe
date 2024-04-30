package com.example.firisbe.authentication.mapper;


import com.example.firisbe.authentication.domain.UserType;
import com.example.firisbe.authentication.dto.UserTypeDTO;
import com.example.firisbe.util.base.BaseDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeDTOMapper extends BaseDTOMapper<UserType, UserTypeDTO> {
}
