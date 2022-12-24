package com.whtl.antipn.services;

import com.whtl.antipn.dto.UserDto;
import com.whtl.antipn.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByEmail(String email);

    UserDto userRegistration(UserDto userDto);
}
