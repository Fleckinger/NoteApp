package com.fleckinger.noteapp.controller;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Principal principal) {
        return principal == null ? "login": "redirect:note/all";
    }

}
