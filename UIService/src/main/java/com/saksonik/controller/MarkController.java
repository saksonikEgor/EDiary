package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.marks.MarksDTO;
import com.saksonik.dto.userfeed.UserfeedDTO;
import com.saksonik.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {
    private final HeadManagerClient headManagerClient;

    @GetMapping("/for-student/{studentId}")
    public Mono<String> getStudentsMarks(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                         @PathVariable("studentId") UUID studentId,
                                         @RequestParam(name = "period", required = false) UUID studyPeriodId,
                                         Model model) {

        return headManagerClient.findAllSubjectsByStudentId(studentId)
                .collectList()
                .doOnNext(subjects -> model.addAttribute("subjects", subjects))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .then(headManagerClient.getMarksForStudentAndPeriod(studentId, studyPeriodId)
                        .flatMap(marks -> {
                            Map<UUID, MarksDTO.SubjectDTO> subjectIdToMarks = new HashMap<>();
                            marks.getSubjects().forEach(s -> subjectIdToMarks.put(s.getId(), s));
                            model.addAttribute("subjectIdToMarks", subjectIdToMarks);

                            return headManagerClient.getUserById(studentId)
                                    .doOnNext(student -> model.addAttribute("student", student))
                                    .thenReturn("marks/for-student");
                        }));
    }

    @GetMapping("/for-class/{classId}/{subjectId}")
    public Mono<String> getMarksForClassAndSubject(
            @ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
            @PathVariable("classId") UUID classId,
            @PathVariable("subjectId") UUID subjectId,
            @RequestParam(name = "period", required = false) UUID studyPeriodId,
            Model model) {
        return headManagerClient.findAllStudentsByClass(classId)
                .collectList()
                .doOnNext(students -> model.addAttribute("students", students))
                .then(headManagerClient.getClassById(classId)
                        .doOnNext(c -> model.addAttribute("class", c)))
                .then(headManagerClient.getMarksForClassAndSubjectAndStudyPeriod(classId, subjectId, studyPeriodId)
                        .flatMap(marks -> {
                            Map<UUID, Map<LocalDate, List<MarksDTO.SubjectDTO.MarkDTO>>> studentIdToDateToMarks
                                    = new HashMap<>();

                            marks.getSubjects()
                                    .getFirst()
                                    .getMarks()
                                    .forEach(m -> {
                                        var dateToMarks = studentIdToDateToMarks
                                                .computeIfAbsent(m.getStudentId(), k -> new HashMap<>());

                                        dateToMarks.computeIfAbsent(m.getDate(), k -> new ArrayList<>())
                                                .add(m);
                                    });

                            model.addAttribute("studentIdToDateToMarks", studentIdToDateToMarks);

                            return headManagerClient.findSubjectById(subjectId)
                                    .doOnNext(s -> {
                                                model.addAttribute("subject", s);
                                                model.addAttribute(
                                                        "period",
                                                        DateUtil.buildPeriod(
                                                                marks.getStartPeriod(),
                                                                marks.getEndPeriod()
                                                        )
                                                );
                                            }
                                    )
                                    .thenReturn("marks/for-class");
                        }));


    }

    @ModelAttribute(name = "userfeed", binding = false)
    public Mono<UserfeedDTO> loadUserfeed() {
        return headManagerClient.getUserfeed();
    }
}
