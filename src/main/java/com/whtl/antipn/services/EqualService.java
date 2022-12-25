package com.whtl.antipn.services;

import com.whtl.antipn.model.User;
import com.whtl.antipn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EqualService {
    private final UserRepository userRepository;

    @Autowired
    public EqualService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> defineUserByEmail(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByEmail(email);

        return user;
    }

}
