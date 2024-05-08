package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.user.UserDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ClassService classService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        User user = userService.findUserById(userId);
        user.buildFullName();
        return ResponseEntity.ok(new UserDTO(userId, user.getFullName()));
    }

    @GetMapping("/students/{classId}")
    public ResponseEntity<List<UserDTO>> getStudentByClassId(@PathVariable UUID classId) {
        Class c = classService.findById(classId);
        List<User> students = userService.findAllStudentsByClass(c);

        return ResponseEntity.ok(students.stream()
                .map(s -> {
                    s.buildFullName();
                    return new UserDTO(s.getUserId(), s.getFullName());
                })
                .toList());
    }
}
