package com.saksonik.usermanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagerController {
    @GetMapping("/ums")
    public String get() {
        return "UMS";
    }
}
