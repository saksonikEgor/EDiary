package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.marks.CreateMarkRequest;
import com.saksonik.headmanager.dto.marks.MarkResponse;
import com.saksonik.headmanager.dto.marks.MarksDTO;
import com.saksonik.headmanager.dto.marks.UpdateMarkRequest;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.service.*;
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
    private final SubjectService subjectService;
    private final WorkTypeService workTypeService;
    private final MarkTypeService markTypeService;

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

    //TODO  переместить это в getMarksByStudent и там проверять роль
//    @GetMapping
//    public ResponseEntity<MarksDTO> getMarksByStudentForTeacher(
//            @RequestHeader("User-Id") UUID teacherId,
//            @RequestParam(name = "class") Integer classId,
//            @RequestParam(name = "period", required = false) String studyPeriodName
//    ) {
//        String role = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getAuthorities()
//                .stream()
//                .findAny()
//                .get()
//                .getAuthority();
//
//        StudyPeriod studyPeriod = studyPeriodName == null
//                ? studyPeriodService.findCurrentStudyPeriod()
//                : studyPeriodService.findStudyPeriodByName(studyPeriodName);
//
//        User teacher = userService.findUserById(teacherId);
//        Class c = classService.findById(classId);
//        Subject subject = teacher.getSubjects().stream().findAny().get(); //TODO  заглушка!!!!!
//
//        MarksDTO marksDTO = new MarksDTO();
//        marksDTO.setRole(role);
//
//        List<Mark> marks = markService.findAllForTeacherAndClass(teacher, c, subject, studyPeriod);
//        marksDTO.setSubjects(buildSubjectDTOs(marks));
//
//        return ResponseEntity.ok(marksDTO);
//    }


    //TODO  проверить роль
    @PostMapping
    public ResponseEntity<MarkResponse> createMark(
            @RequestHeader("User-Id") UUID teacherId,
            @RequestBody CreateMarkRequest request) {
        User teacher = userService.findUserById(teacherId);
        User student = userService.findUserById(request.studentId());
        Subject subject = subjectService.findById(request.subjectId());
        WorkType workType = workTypeService.findById(request.workTypeId());
        MarkType markType = markTypeService.findById(request.markTypeId());
        StudyPeriod studyPeriod = studyPeriodService.findStudyPeriodByDate(request.date());

        Mark mark = markService.createMark(request, student, teacher, subject, markType, workType, studyPeriod);

        return ResponseEntity.ok(new MarkResponse(
                mark.getMarkId(),
                mark.getCreatedAt(),
                mark.getLastModifiedAt(),
                mark.getDate(),
                mark.getDescription(),
                mark.getStudent().getFullName(),
                mark.getTeacher().getFullName(),
                mark.getSubject().getName(),
                mark.getWorkType().getName(),
                mark.getMarkType().getType(),
                mark.getStudyPeriod().getName()
        ));
    }

    //TODO  проверить роль и что данный учитель редактикует свою же оценку
    @PatchMapping("/{id}")
    public ResponseEntity<MarkResponse> updateMark(
            @RequestHeader("User-Id") UUID teacherId,
            @PathVariable("id") Integer markId,
            @RequestBody UpdateMarkRequest request) {
        WorkType workType = workTypeService.findById(request.workTypeId());
        MarkType markType = markTypeService.findById(request.markTypeId());

        Mark mark = markService.updateMark(markId, request, markType, workType);

        return ResponseEntity.ok(new MarkResponse(
                mark.getMarkId(),
                mark.getCreatedAt(),
                mark.getLastModifiedAt(),
                mark.getDate(),
                mark.getDescription(),
                mark.getStudent().getFullName(),
                mark.getTeacher().getFullName(),
                mark.getSubject().getName(),
                mark.getWorkType().getName(),
                mark.getMarkType().getType(),
                mark.getStudyPeriod().getName()
        ));
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
