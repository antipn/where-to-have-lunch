package com.whtl.antipn.controllers;

import com.whtl.antipn.dto.UserDto;
import com.whtl.antipn.model.User;
import com.whtl.antipn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public ModelAndView showingLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            ModelAndView login = new ModelAndView("login");
            return login;
        }
        ModelAndView welcome = new ModelAndView("welcome");
        return welcome;
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(@ModelAttribute("userDto") UserDto userDto) {
        return new ModelAndView("registration");

    }


    @PostMapping(value = "/registration")
    public ModelAndView doRegistration(@ModelAttribute("userDto") UserDto userDto) {
        String password = userDto.getPassword();
        String confirmPassword = userDto.getConfirmPassword();

        if (password.equals(confirmPassword)) { //if pass1 field == pass2 field
            try {
                userService.userRegistration(userDto);
                return new ModelAndView("login");
            } catch (RuntimeException e) {
                return new ModelAndView("registration");
            }
        }
        return new ModelAndView("registration");
    }

    @GetMapping("/welcome")
    public ModelAndView welcomePage() throws UsernameNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Optional<User> user = userService.findByEmail(auth.getName());
            if (user.isEmpty()) throw new UsernameNotFoundException("Пользователь не найден");
            String email = user.get().getEmail();
            ModelAndView welcome = new ModelAndView("welcome");
            welcome.addObject("email", email);
            return welcome;

        } catch (UsernameNotFoundException exception) {
            ModelAndView login = new ModelAndView("login");
            return login;
        }

    }
}
