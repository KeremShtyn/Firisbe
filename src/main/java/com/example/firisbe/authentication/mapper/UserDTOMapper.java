package com.example.firisbe.authentication.mapper;


import com.example.firisbe.authentication.domain.User;
import com.example.firisbe.authentication.service.dto.UserDTO;
import com.example.firisbe.util.base.BaseDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper extends BaseDTOMapper<User, UserDTO> {
}
