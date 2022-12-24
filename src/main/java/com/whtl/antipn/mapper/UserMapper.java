package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.UserDto;
import com.whtl.antipn.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User mapFromDto(UserDto dto);

    @Mapping(target = "confirmPassword", source = "user.password")
    UserDto mapToDto(User user);

}
