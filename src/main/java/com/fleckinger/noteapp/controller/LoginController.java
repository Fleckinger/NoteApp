package com.fleckinger.noteapp.controller;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        //TODO в хтмл страницы логина добавить ссылку на страницу регистрации, для тех у кого нет аккаунта
        return "login";
    }

}
