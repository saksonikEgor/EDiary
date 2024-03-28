package com.saksonik.usermanager.controller;

import com.saksonik.usermanager.dto.AuthRequest;
import com.saksonik.usermanager.security.JWTHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JWTHandler jwtHandler;

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid AuthRequest authRequest) {
        registrationService.register(person);
        String token = jwtHandler.generateToken(authRequest.login());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }
}
