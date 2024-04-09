package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.userfeed.UserfeedDTO;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping("/userfeed")
@RequiredArgsConstructor
public class UserFeedController {
    private final UserService userService;

    //TODO  передавать в хедере токен
    //TODO  обработать исключение userIsNotFound
    //TODO  добавить логику для админа
    @GetMapping
    public ResponseEntity<UserfeedDTO> getUserFeed(@RequestHeader("User-Id") UUID userId) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        UserfeedDTO userfeedDTO = new UserfeedDTO();
        userfeedDTO.setId(userId);
        userfeedDTO.setRole(role);

        switch (role) {
            case "ROLE_STUDENT", "ROLE_CLASSROOM-TEACHER" -> {
            }
            case "ROLE_PARENT" -> userfeedDTO.setChildren(userService.findUserById(userId)
                    .getChildren()
                    .stream()
                    .map(user -> new UserfeedDTO.Child(
                            user.getUserId(),
                            user.getFullName(),
                            user.getStudentDistribution().getClazz().getName()))
                    .toList()
            );
            case "ROLE_TEACHER" -> userfeedDTO.setClasses(userService.findUserById(userId)
                    .getClassesForTeacher()
                    .stream()
                    .map(c -> new UserfeedDTO.Class(c.getClassId(), c.getName()))
                    .toList()
            );
//            case "ROLE_ADMIN" -> {
//            }
        }

        return ResponseEntity.ok(userfeedDTO);
    }


//    @GetMapping("/student")
//    public ResponseEntity<UserDTO> getStudentUserFeed() {
//        var user = new UserDTO("Student ivan", "ivan@mail.ru");
//
//        System.out.println("/student");
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/parent")
//    public ResponseEntity<UserDTO> getParentUserFeed() {
//        var user = new UserDTO("Teacher maria", "maria@mail.ru");
//
//        return ResponseEntity.ok(user);
//    }
//
//
//    @GetMapping("/teacher")
//    public String getTeacherUserFeed() {
//        System.out.println("teacher/");
//        return "hm";
//    }
//
//    @GetMapping("/classroom-teacher")
//    public String getClassroomTeacherUserFeed() {
//        return "hm";
//    }
//
//    @Data
//    @AllArgsConstructor
//    public class UserDTO {
//        String login;
//        String email;
//    }

    //TODO
    private String getUserIdFromToken() {
        return "yft324s234y11s3";
//        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
//         (User)authenticationToken.getPrincipal();
//
//        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        JWTAssertionDetails.
//        OAuth2ResourceServerProperties.Jwt jwt = (OAuth2ResourceServerProperties.Jwt) authenticationToken.getCredentials();
//        String email = (String) jwt.getClaims().get("email");
//        return email;
    }


}
