package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.marks.CreateMarkRequest;
import com.saksonik.headmanager.dto.marks.MarkResponse;
import com.saksonik.headmanager.dto.marks.MarksDTO;
import com.saksonik.headmanager.dto.marks.UpdateMarkRequest;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
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

    @GetMapping("/for-student/{studentId}")
    public ResponseEntity<MarksDTO> getMarksByStudent(
            @PathVariable("studentId") UUID studentId,
            @RequestParam(name = "period", required = false) UUID studyPeriodId) {
        StudyPeriod studyPeriod = studyPeriodId == null
                ? studyPeriodService.findCurrentStudyPeriod()
                : studyPeriodService.findById(studyPeriodId);

        MarksDTO marksDTO = new MarksDTO();

        User student = userService.findUserById(studentId);
        List<Mark> marks = markService.findAllByStudentAndStudyPeriod(student, studyPeriod);
        marksDTO.setSubjects(buildSubjectDTOs(marks));
        marksDTO.setStudyPeriodName(studyPeriod.getName());
        marksDTO.setStartPeriod(studyPeriod.getStart());
        marksDTO.setEndPeriod(studyPeriod.getEnd());

        log.info("Getting marks {} for studentId {}", marks, studentId);
        return ResponseEntity.ok(marksDTO);
    }

//    @GetMapping("/for-student/{id}")
//    public ResponseEntity<MarksDTO> getMarksByStudentForParent(
//            @RequestHeader("User-Id") UUID userId,
//            @RequestHeader("Role") String role,
//            @PathVariable("id") UUID studentId,
//            @RequestParam(name = "period", required = false) UUID studyPeriodId) {
//        StudyPeriod studyPeriod = studyPeriodId == null
//                ? studyPeriodService.findCurrentStudyPeriod()
//                : studyPeriodService.findById(studyPeriodId);
//
//        MarksDTO marksDTO = new MarksDTO();
//        marksDTO.setRole(role);
//
//        User student = userService.findUserById(studentId);
//        List<Mark> marks = markService.findAllByStudentAndStudyPeriod(student, studyPeriod);
//        marksDTO.setSubjects(buildSubjectDTOs(marks));
//
//        return ResponseEntity.ok(marksDTO);
//    }

    @GetMapping("/for-class/{classId}/{subjectId}")
    public ResponseEntity<MarksDTO> getMarksForClassAndSubject(
            @PathVariable("classId") UUID classId,
            @PathVariable("subjectId") UUID subjectId,
            @RequestParam(name = "period", required = false) UUID studyPeriodId) {
        StudyPeriod studyPeriod = studyPeriodId == null
                ? studyPeriodService.findCurrentStudyPeriod()
                : studyPeriodService.findById(studyPeriodId);

        Class c = classService.findById(classId);
        Subject subject = subjectService.findById(subjectId);

        MarksDTO marksDTO = new MarksDTO();
        List<Mark> marks = markService.findAllByClassIdAndSubjectAndStudyPeriod(classId, subject, studyPeriod);
        marksDTO.setSubjects(buildSubjectDTOs(marks));
        marksDTO.setStudyPeriodName(studyPeriod.getName());
        marksDTO.setStartPeriod(studyPeriod.getStart());
        marksDTO.setEndPeriod(studyPeriod.getEnd());

        log.info("Building marks {} for classId {} and subjectId {}", marksDTO, c, subjectId);
        return ResponseEntity.ok(marksDTO);
    }


    @PostMapping
    public ResponseEntity<MarkResponse> createMark(@RequestHeader("User-Id") UUID userId,
                                                   @RequestBody CreateMarkRequest request) {
        User teacher = userService.findUserById(userId);
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

    @PatchMapping("/{id}")
    public ResponseEntity<MarkResponse> updateMark(@PathVariable("id") UUID markId,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable("id") UUID markId) {
        markService.deleteMarkById(markId);
        return ResponseEntity.ok().build();
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

                    subjectDTO.setId(e.getKey().getSubjectId());
                    subjectDTO.setSubjectName(e.getKey().getName());
                    subjectDTO.setMarks(e.getValue().stream()
                            .map(mark -> new MarksDTO.SubjectDTO.MarkDTO(
                                    mark.getMarkId(),
                                    mark.getStudent().getUserId(),
                                    mark.getTeacher().getUserId(),
                                    mark.getWorkType().getName(),
                                    mark.getMarkType().getType(),
                                    mark.getCreatedAt(),
                                    mark.getLastModifiedAt(),
                                    mark.getDate(),
                                    mark.getDescription()))
                            .toList());
                    subjectDTO.setAvg(markService.calculateAvg(e.getValue()));

                    return subjectDTO;
                })
                .toList();
    }
}
