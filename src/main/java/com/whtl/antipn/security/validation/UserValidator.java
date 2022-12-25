package com.whtl.antipn.security.validation;

import com.whtl.antipn.dto.UserDto;
import com.whtl.antipn.model.User;
import com.whtl.antipn.services.EqualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class UserValidator implements Validator {
    private final EqualService equalService;

    @Autowired
    public UserValidator(EqualService equalService) {
        this.equalService = equalService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        Optional<User> userEmail = equalService.defineUserByEmail(userDto.getEmail());

        if (userEmail.isPresent()) {
            errors.rejectValue("email", "", "Your email has already registered, please try another");
        }

        String password = userDto.getPassword();
        String confirmPassword = userDto.getConfirmPassword();

        if (password.length() < 5) {
            errors.rejectValue("password", "", "Password less than 5 characters");
        }

        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "", "Passwords are not equal, please try again");
        }


    }
}
