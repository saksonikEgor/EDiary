package com.saksonik.authenticator.controller;

import com.saksonik.authenticator.dto.AuthRequest;
import com.saksonik.authenticator.dto.AuthResponse;
import com.saksonik.authenticator.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        log.info("Auth || POST /register with request: " + request);

        AuthResponse response = userService.register(request);
        log.info("Auth || generated tokens: " + request);
        return ResponseEntity.ok(response);
    }
}
