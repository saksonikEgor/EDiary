package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.UserDTO;
import com.saksonik.headmanager.dto.profile.CreateProfileRequest;
import com.saksonik.headmanager.exception.NoAuthorityException;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Subject;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    //TODO  передавать в хедере токен
    //TODO  обработать исключение userIsNotFound
    //TODO  добавить логику для админа
    //TODO  добавить post запрос на изменение данных в профиле
    @GetMapping
    public ResponseEntity<UserDTO> getProfile(
            @RequestHeader("User-Id") UUID userId,
            @RequestHeader("Role") String role
    ) {
//        String role = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getAuthorities()
//                .stream()
//                .findAny()
//                .get()
//                .getAuthority();

        User user = userService.findUserById(userId);

        UserDTO userDTO = new UserDTO();
        userDTO.setFullName(user.getFullName());
        userDTO.setUserId(userId);
        userDTO.setRole(role);

        switch (role) {
            case "ROLE_STUDENT" -> {
                userDTO.setParents(user.getParents().stream()
                        .map(User::getFullName)
                        .toList());

                userDTO.setClassForStudent(user.getStudentDistribution()
                        .getClazz().getName());
            }
            case "ROLE_PARENT" -> userDTO.setChildren(user.getChildren().stream()
                    .map(u -> new UserDTO.ChildDTO(
                            u.getFullName(),
                            u.getStudentDistribution().getClazz().getName()))
                    .toList()
            );
            case "ROLE_TEACHER" -> {
                userDTO.setClassesForTeacher(user.getClassesForTeacher().stream()
                        .map(Class::getName)
                        .toList());

                userDTO.setSubjects(user.getSubjects().stream()
                        .map(Subject::getName)
                        .toList());
            }
            case "ROLE_CLASSROOM-TEACHER" -> userDTO.setClassesForClassroomTeacher(user.getClassesForClassroomTeacher().stream()
                    .map(Class::getName)
                    .toList());
            case null, default -> throw new NoAuthorityException("Not allowed to access this meeting");
//            case "ROLE_ADMIN" -> {
//            }
        }

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<Void> createProfile(
            @RequestHeader("User-Id") UUID userId,
            @RequestHeader("Role") String role,
            @RequestBody CreateProfileRequest createProfileRequest
    ) {
        userService.save(createProfileRequest);
        return ResponseEntity.ok().build();
    }


    //TODO  добавить создание изменение и удаление профиля

}
