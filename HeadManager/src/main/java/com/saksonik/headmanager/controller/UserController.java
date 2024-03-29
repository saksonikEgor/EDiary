package com.saksonik.headmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userfeed")
public class UserController {
    @GetMapping("/get")
    public String get() {
        return "hm";
    }

//    @GetMapping("/")
//    public String internal(){
//        return "internal work";
//    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> studentUserFeed() {
        // Код, который должен выполняться, если пользователь имеет роль 'ROLE_USER'
        return ResponseEntity.ok("Access granted for user.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public ResponseEntity<?> parentUserFeed() {
        // Код, который должен выполняться, если пользователь имеет роль 'ROLE_USER'
        return ResponseEntity.ok("Access granted for user.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_TACHER')")
    public ResponseEntity<?> teacherUserFeed() {
        // Код, который должен выполняться, если пользователь имеет роль 'ROLE_USER'
        return ResponseEntity.ok("Access granted for user.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CLASSROOM-TEACHER')")
    public ResponseEntity<?> classroomTeacherUserFeed() {
        // Код, который должен выполняться, если пользователь имеет роль 'ROLE_USER'
        return ResponseEntity.ok("Access granted for user.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminUserFeed() {
        // Код, который должен выполняться, если пользователь имеет роль 'ROLE_ADMIN'
        return ResponseEntity.ok("Access granted for admin.");
    }
}
