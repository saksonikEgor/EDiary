package com.saksonik.notificator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificatorController {
    @GetMapping("/n")
    public String get() {
        System.out.println("/n");
        return "Success!";
    }
}
