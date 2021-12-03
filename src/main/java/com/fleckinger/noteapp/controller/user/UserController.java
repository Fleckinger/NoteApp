package com.fleckinger.noteapp.controller.user;

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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

        if (!userDto.getEmail().equals(userDto.getEmailConfirm()) || userDto.getEmail().isBlank()) {

            bindingResult.rejectValue("emailConfirm", "Emails don't match", "Emails don't match");

        } else if (userDto.getEmail().equals(userService.getCurrentUser().getEmail())
                && userService.userExists(userService.getCurrentUser().getEmail())) {

            bindingResult.rejectValue("emailConfirm", "That email already in use",
                    "That email already in use");

        } else {
            User user = UserConverter.dtoToEntity(userDto);
            user.setId(userService.getCurrentUser().getId());
            user.setRole("ROLE_USER"); //TODO поменять роли на enum
            user.setPassword(userService.getCurrentUser().getPassword());
            userService.update(user);
        }

        return "userManage/profile";
    }

    @GetMapping("/profile/edit/email")
    public String editEmail(Model model) {
        model.addAttribute("email", "");
        model.addAttribute("emailConfirm", "");
        return "userManage/editEmail";
    }

    @GetMapping("/profile/edit/password")
    public String editPassword(Model model) {
        model.addAttribute("password", "");
        model.addAttribute("passwordConfirm", "");
        return "userManage/editPassword";
    }

    @PostMapping("profile/edit/password")
    public String updatePassword(@ModelAttribute String password, @ModelAttribute String passwordConfirm,
                                 BindingResult bindingResult) {
        if (!password.equals(passwordConfirm)) {
            bindingResult.rejectValue("passwordConfirm", "Passwords don't match",
                    "Passwords don't match");
            //TODO проверить будет ли при несовпадающих паролях зависать на той же странице или нужно добавить редирект
        } else {
            User user = userService.getCurrentUser();
            user.setPassword(password);
            userService.updatePassword(user);
        }
        return "userManage/profile";
    }
}
