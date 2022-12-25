package com.whtl.antipn.security;

import com.whtl.antipn.dto.UserDto;
import com.whtl.antipn.model.User;

import java.io.Serial;


public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private UserDto userDto;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        setTo(new UserDto(user.getId(), user.getEmail(), user.getPassword(), user.getPassword()));
    }

    public int getId() {
        return userDto.getId();
    }

    public void setTo(UserDto newTo) {
        newTo.setPassword(null);
        userDto = newTo;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
