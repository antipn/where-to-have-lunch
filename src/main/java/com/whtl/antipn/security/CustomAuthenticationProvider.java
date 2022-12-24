package com.whtl.antipn.security;

import com.whtl.antipn.model.User;
import com.whtl.antipn.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public CustomAuthenticationProvider(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> targetUser = userServiceImpl.findByEmail(email);

        if (targetUser.isEmpty()) {
            throw new BadCredentialsException("Unknown email " + email);
        }
        if (!password.equals(targetUser.get().getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
