package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.MarksDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.MarkService;
import com.saksonik.headmanager.service.StudyPeriodService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {
    private final ClassService classService;
    private final UserService userService;
    private final MarkService markService;
    private final StudyPeriodService studyPeriodService;

    //TODO  добавить post запрос для добавления оценок (для учителя)
    //TODO  проверить имеет юзер права
    //TODO  добавить обработку исключения studyPeriodNotFound
    //TODO  добавить обработку исключения studentNotFound
    //TODO  добавить обработку исключения classNotFound
    @GetMapping
    public ResponseEntity<MarksDTO> getMarksByStudent(
            @RequestHeader("User-Id") UUID studentId,
            @RequestParam(name = "period", required = false) String studyPeriodName
    ) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        StudyPeriod studyPeriod = studyPeriodName == null
                ? studyPeriodService.findCurrentStudyPeriod()
                : studyPeriodService.findStudyPeriodByName(studyPeriodName);

        MarksDTO marksDTO = new MarksDTO();
        marksDTO.setRole(role);

        User student = userService.findUserById(studentId);
        List<Mark> marks = markService.findAllByStudentAndStudyPeriod(student, studyPeriod);
        marksDTO.setSubjects(buildSubjectDTOs(marks));

        return ResponseEntity.ok(marksDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarksDTO> getMarksByStudentForParent(
            @RequestHeader("User-Id") UUID parentId,
            @PathVariable("id") UUID studentId,
            @RequestParam(name = "period", required = false) String studyPeriodName
    ) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        StudyPeriod studyPeriod = studyPeriodName == null
                ? studyPeriodService.findCurrentStudyPeriod()
                : studyPeriodService.findStudyPeriodByName(studyPeriodName);

        MarksDTO marksDTO = new MarksDTO();
        marksDTO.setRole(role);

        User student = userService.findUserById(studentId);
        List<Mark> marks = markService.findAllByStudentAndStudyPeriod(student, studyPeriod);
        marksDTO.setSubjects(buildSubjectDTOs(marks));

        return ResponseEntity.ok(marksDTO);
    }

    @GetMapping
    public ResponseEntity<MarksDTO> getMarksByStudentForTeacher(
            @RequestHeader("User-Id") UUID teacherId,
            @RequestParam(name = "class") Integer classId,
            @RequestParam(name = "period", required = false) String studyPeriodName
    ) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        StudyPeriod studyPeriod = studyPeriodName == null
                ? studyPeriodService.findCurrentStudyPeriod()
                : studyPeriodService.findStudyPeriodByName(studyPeriodName);

        User teacher = userService.findUserById(teacherId);
        Class c = classService.findById(classId);
        Subject subject = teacher.getSubjects().stream().findAny().get(); //TODO  заглушка!!!!!

        MarksDTO marksDTO = new MarksDTO();
        marksDTO.setRole(role);

        List<Mark> marks = markService.findAllForTeacherAndClass(teacher, c, subject, studyPeriod);
        marksDTO.setSubjects(buildSubjectDTOs(marks));

        return ResponseEntity.ok(marksDTO);
    }

    private List<MarksDTO.SubjectDTO> buildSubjectDTOs(List<Mark> marks) {
        Map<Subject, List<Mark>> subjectMarks = new HashMap<>();

        marks.forEach(mark -> {
            Subject subject = mark.getSubject();

            List<Mark> markList = subjectMarks.getOrDefault(subject, new ArrayList<>());

            markList.add(mark);
            subjectMarks.put(subject, markList);
        });

        return subjectMarks.entrySet().stream()
                .map(e -> {
                    MarksDTO.SubjectDTO subjectDTO = new MarksDTO.SubjectDTO();

                    subjectDTO.setSubjectName(e.getKey().getName());
                    subjectDTO.setMarks(e.getValue().stream()
                            .map(mark -> new MarksDTO.SubjectDTO.MarkDTO(
                                    mark.getWorkType().getName(),
                                    mark.getMarkType().getType(),
                                    mark.getCreatedAt(),
                                    mark.getLastModifiedAt(),
                                    mark.getDescription()))
                            .toList());
                    subjectDTO.setAvg(markService.calculateAvg(e.getValue()));

                    return subjectDTO;
                })
                .toList();
    }
}
