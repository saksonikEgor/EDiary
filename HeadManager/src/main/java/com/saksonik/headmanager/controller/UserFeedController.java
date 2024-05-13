package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.userfeed.UserfeedDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Subject;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/userfeed")
@RequiredArgsConstructor
public class UserFeedController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserfeedDTO> getUserFeed(JwtAuthenticationToken authenticationToken) {
        UUID userId = UUID.fromString(authenticationToken.getToken().getSubject());

        User user = userService.findUserById(userId);
        UserfeedDTO userfeedDTO = new UserfeedDTO();

        userfeedDTO.setUserId(userId);
        List<String> roles = authenticationToken
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        userfeedDTO.setRoles(roles);

        if (roles.contains("ROLE_STUDENT")) {
            Class c = user.getStudentDistribution().getClazz();
            userfeedDTO.setStudyingClass(new UserfeedDTO.ClassDTO(c.getClassId(), c.getName()));
        }
        if (roles.contains("ROLE_PARENT")) {
            List<User> children = user.getChildren();
            userfeedDTO.setChildren(children.stream()
                    .peek(User::buildFullName)
                    .map(c -> new UserfeedDTO.ChildDTO(
                            c.getUserId(),
                            c.getFullName(),
                            new UserfeedDTO.ClassDTO(
                                    c.getStudentDistribution().getClazz().getClassId(),
                                    c.getStudentDistribution().getClazz().getName()
                            )))
                    .toList());
        }
        if (roles.contains("ROLE_TEACHER")) {
            List<UserfeedDTO.ClassToSubjectsDTO> classesToSubjects = new ArrayList<>();
            List<Class> classes = user.getClassesForTeacher();
            Set<Subject> subjects = new HashSet<>(user.getSubjects());

            for (Class c : classes) {
                Set<Subject> subjectsForClass = new HashSet<>(c.getSubjects());
                subjectsForClass.retainAll(subjects);

                classesToSubjects.add(new UserfeedDTO.ClassToSubjectsDTO(
                        new UserfeedDTO.ClassDTO(c.getClassId(), c.getName()),
                        subjectsForClass.stream()
                                .map(s -> new UserfeedDTO.SubjectDTO(s.getSubjectId(), s.getName()))
                                .toList()));
            }
            userfeedDTO.setTeachingClasses(classesToSubjects);
        }
        if (roles.contains("ROLE_CLASSROOM_TEACHER")) {
            userfeedDTO.setManagedClasses(user.getClassesForClassroomTeacher()
                    .stream()
                    .map(c -> new UserfeedDTO.ClassDTO(c.getClassId(), c.getName()))
                    .toList());
        }

        log.info("Building user feed {}", userfeedDTO);
        return ResponseEntity.ok(userfeedDTO);
    }

}
