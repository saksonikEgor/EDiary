package com.saksonik.headmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/hm")
    public String get() {
        return "hm";
    }
}
