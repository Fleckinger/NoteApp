package com.fleckinger.noteapp.controller.user;

import com.fleckinger.noteapp.dto.user.UserConverter;
import com.fleckinger.noteapp.dto.user.UserDto;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.security.Role;
import com.fleckinger.noteapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserDto dto = UserConverter.entityToDto(userService.getCurrentUser());
        dto.setEmailConfirm(userService.getCurrentUser().getEmail());

        model.addAttribute("userDto", dto);
        return "userManage/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {
        //TODO как-то убрать зашифрованный пароль из формы
        if (!userDto.getEmail().equals(userDto.getEmailConfirm()) || userDto.getEmail().isBlank()) {

            bindingResult.rejectValue("emailConfirm", "Emails don't match", "Emails don't match");

        } else if (!userDto.getEmail().equals(userService.getCurrentUser().getEmail())
                && userService.userExists(userService.getCurrentUser().getEmail())) {

            bindingResult.rejectValue("emailConfirm", "That email already in use",
                    "That email already in use");

        } else if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "Passwords don't match",
                    "Passwords don't match");
        } else {
            User user = UserConverter.dtoToEntity(userDto);
            user.setId(userService.getCurrentUser().getId());
            user.setRole(Role.ROLE_USER);
            if (userDto.getPassword().isEmpty()) {
                user.setPassword(userService.getCurrentUser().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userService.update(user);
        }

        return "userManage/profile";
    }
}
