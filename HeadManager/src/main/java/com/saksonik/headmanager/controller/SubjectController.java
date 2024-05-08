package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.subject.SubjectDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.StudentDistribution;
import com.saksonik.headmanager.model.Subject;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.StudentDistributionService;
import com.saksonik.headmanager.service.SubjectService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final ClassService classService;
    private final UserService userService;
    private final StudentDistributionService studentDistributionService;
    private final SubjectService subjectService;

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable UUID subjectId) {
        Subject subject = subjectService.findById(subjectId);
        return ResponseEntity.ok(new SubjectDTO(subject.getSubjectId(), subject.getName()));
    }

    @GetMapping("/for-class/{classId}")
    public ResponseEntity<List<SubjectDTO>> getSubjectsForClass(@PathVariable("classId") UUID classId) {
        Class c = classService.findById(classId);
        List<Subject> subjects = c.getSubjects();

        return ResponseEntity.ok(subjects.stream()
                .map(s -> new SubjectDTO(s.getSubjectId(), s.getName()))
                .toList());
    }

    @GetMapping("/for-student/{studentId}")
    public ResponseEntity<List<SubjectDTO>> getSubjectsForStudent(@PathVariable("studentId") UUID studentId) {
        User student = userService.findUserById(studentId);
        StudentDistribution sd = studentDistributionService.findByStudent(student);
        Class c = sd.getClazz();

        List<Subject> subjects = c.getSubjects();

        return ResponseEntity.ok(subjects.stream()
                .map(s -> new SubjectDTO(s.getSubjectId(), s.getName()))
                .toList());
    }
}
