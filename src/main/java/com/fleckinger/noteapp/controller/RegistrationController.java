package com.fleckinger.noteapp.controller;

import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registrationSubmit(@ModelAttribute User user, Model model) {
        model.getAttribute("user");

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            //TODO не выводит надпись о несовпадении паролей
            return new ModelAndView("registration");
        }

        userService.register(user);
        return new ModelAndView("redirect:note/all");
    }
}
