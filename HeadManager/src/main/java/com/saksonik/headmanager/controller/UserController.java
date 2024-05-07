package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.user.UserDTO;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        User user = userService.findUserById(userId);
        user.buildFullName();
        return ResponseEntity.ok(new UserDTO(userId, user.getFullName()));
    }
}
