package com.fleckinger.noteapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String main(Principal principal) {
        return principal == null ? "main": "redirect:note/all";
    }
}
