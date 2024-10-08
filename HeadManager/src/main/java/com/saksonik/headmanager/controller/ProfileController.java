package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.profile.CreateProfileRequest;
import com.saksonik.headmanager.dto.profile.ProfileDTO;
import com.saksonik.headmanager.exception.NoAuthorityException;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Subject;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

//    @GetMapping("/{userId}")
//    public ResponseEntity<ProfileDTO> getProfile(@PathVariable("userId") UUID userId,
//                                                 @RequestHeader("role") String role) {
//        User user = userService.findUserById(userId);
//
//        ProfileDTO userDTO = new ProfileDTO();
//        userDTO.setFullName(user.getFullName());
//        userDTO.setUserId(userId);
//        userDTO.setRole(role);
//
//        switch (role) {
//            case "ROLE_STUDENT" -> {
//                userDTO.setParents(user.getParents().stream()
//                        .map(User::getFullName)
//                        .toList());
//
//                userDTO.setClassForStudent(user.getStudentDistribution()
//                        .getClazz().getName());
//            }
//            case "ROLE_PARENT" -> userDTO.setChildren(user.getChildren().stream()
//                    .map(u -> new ProfileDTO.ChildDTO(
//                            u.getFullName(),
//                            u.getStudentDistribution().getClazz().getName()))
//                    .toList()
//            );
//            case "ROLE_TEACHER" -> {
//                userDTO.setClassesForTeacher(user.getClassesForTeacher().stream()
//                        .map(Class::getName)
//                        .toList());
//
//                userDTO.setSubjects(user.getSubjects().stream()
//                        .map(Subject::getName)
//                        .toList());
//            }
//            case "ROLE_CLASSROOM-TEACHER" ->
//                    userDTO.setClassesForClassroomTeacher(user.getClassesForClassroomTeacher().stream()
//                            .map(Class::getName)
//                            .toList());
//            case null, default -> throw new NoAuthorityException("Not allowed to access this meeting");
//        }
//
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @PostMapping
//    public ResponseEntity<Void> createProfile(@RequestBody CreateProfileRequest createProfileRequest) {
//        userService.save(createProfileRequest);
//        return ResponseEntity.ok().build();
//    }
}
