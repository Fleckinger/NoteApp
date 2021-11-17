package com.fleckinger.noteapp.controller;

import com.fleckinger.noteapp.dto.user.UserConverter;
import com.fleckinger.noteapp.dto.user.UserDto;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {

        if (!userDto.getEmail().equals(userDto.getEmailConfirm()) || userDto.getEmail().isBlank()) {
            bindingResult.rejectValue("emailConfirm", "Emails don't match",
                    "Emails don't match");
        } else if (userService.userExists(userDto.getEmail())) {
            bindingResult.rejectValue("email", "User already exists",
                    "User with this email already exists");
        }

        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "Passwords don't match",
                    "Passwords don't match");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = UserConverter.dtoToEntity(userDto);
        userService.register(user);
        return "redirect:note/all";
    }
}
