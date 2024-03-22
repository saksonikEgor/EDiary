package com.saksonik.authenticator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/ac")
    public String get() {
        return "ac";
    }
}
