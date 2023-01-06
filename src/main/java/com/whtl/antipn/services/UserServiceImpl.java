package com.whtl.antipn.services;

import com.whtl.antipn.security.AuthorizedUser;
import com.whtl.antipn.dto.UserDto;
import com.whtl.antipn.mapper.UserMapper;
import com.whtl.antipn.model.User;
import com.whtl.antipn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.whtl.antipn.model.Role.USER;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public UserDto userRegistration(UserDto userDto) {
        User entity = UserMapper.USER_MAPPER.mapFromDto(userDto);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRoles(Set.of(USER));
        if ((userRepository.findUserByEmail(entity.getEmail())).isPresent()) {
            throw new RuntimeException("User with such email " + entity.getEmail() + " already exits.");
        }
        userRepository.save(entity);
        return UserMapper.USER_MAPPER.mapToDto(entity);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user.get());
    }
}
