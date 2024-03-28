package com.saksonik.headmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {
    @GetMapping("/login")
    public String get() {
        return "hm";
    }

    @GetMapping("/internal")
    public String internal(){
        return "internal work";
    }
}
