package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.marks.CreateMarkRequest;
import com.saksonik.dto.marks.MarksDTO;
import com.saksonik.dto.userfeed.UserfeedDTO;
import com.saksonik.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
                .then(headManagerClient.getAllStudyPeriods()
                        .collectList()
                        .doOnNext(sp -> model.addAttribute("studyPeriods", sp)))
                .then(headManagerClient.getMarksForStudentAndPeriod(studentId, studyPeriodId)
                        .flatMap(marks -> {
                            Map<UUID, MarksDTO.SubjectDTO> subjectIdToMarks = new HashMap<>();
                            marks.getSubjects().forEach(s -> subjectIdToMarks.put(s.getId(), s));
                            model.addAttribute("subjectIdToMarks", subjectIdToMarks);
                            model.addAttribute("currentStudyPeriodId", marks.getStudyPeriodId());

                            return headManagerClient.getUserById(studentId)
                                    .doOnNext(student -> model.addAttribute("student", student))
                                    .thenReturn("marks/for-student");
                        }));
    }

    @GetMapping("/for-class/{classId}/{subjectId}")
    public Mono<String> getMarksForClassAndSubject(
            @ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
            @ModelAttribute("request") CreateMarkRequest request,
            @PathVariable("classId") UUID classId,
            @PathVariable("subjectId") UUID subjectId,
            @RequestParam(name = "period", required = false) UUID studyPeriodId,
            Model model) {
        model.addAttribute("request", request);

        return headManagerClient.findAllStudentsByClass(classId)
                .collectList()
                .doOnNext(students -> model.addAttribute("students", students))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .then(headManagerClient.getClassById(classId)
                        .doOnNext(c -> model.addAttribute("class", c)))
                .then(headManagerClient.getAllMarkTypes()
                        .collectList()
                        .doOnNext(mts -> model.addAttribute("markTypes", mts)))
                .then(headManagerClient.getAllWorkTypes()
                        .collectList()
                        .doOnNext(wts -> model.addAttribute("workTypes", wts)))
                .then(headManagerClient.getAllStudyPeriods()
                        .collectList()
                        .doOnNext(sp -> model.addAttribute("studyPeriods", sp)))
                .then(headManagerClient.getMarksForClassAndSubjectAndStudyPeriod(classId, subjectId, studyPeriodId)
                        .flatMap(marks -> {
                            Map<UUID, Map<LocalDate, List<MarksDTO.SubjectDTO.MarkDTO>>> studentIdToDateToMarks
                                    = new HashMap<>();

                            if (!marks.getSubjects().isEmpty()) {
                                marks.getSubjects()
                                        .getFirst()
                                        .getMarks()
                                        .forEach(m -> {
                                            var dateToMarks = studentIdToDateToMarks
                                                    .computeIfAbsent(m.getStudentId(), k -> new HashMap<>());

                                            dateToMarks.computeIfAbsent(m.getDate(), k -> new ArrayList<>())
                                                    .add(m);
                                        });
                            }
                            model.addAttribute("studentIdToDateToMarks", studentIdToDateToMarks);
                            model.addAttribute("currentStudyPeriodId", marks.getStudyPeriodId());

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

    @PostMapping("/create/{subjectId}/{studentId}/{classId}")
    public Mono<String> createMark(@PathVariable("subjectId") UUID subjectId,
                                   @PathVariable("studentId") UUID studentId,
                                   @PathVariable("classId") UUID classId,
                                   @RequestParam(value = "year") Integer year,
                                   @RequestParam(value = "month") Integer month,
                                   @RequestParam(value = "day") Integer day,
                                   @ModelAttribute("request") CreateMarkRequest request,
                                   BindingResult bindingResult,
                                   Model model) {
        log.info("Creating mark {}", request);


        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("request", request);

            return Mono.just("marks/for-class");
        } else {
            request.setStudentId(studentId);
            request.setSubjectId(subjectId);
            request.setDate(LocalDate.of(year, month, day));

            return headManagerClient.createMark(request)
                    .thenReturn("redirect:/marks/for-class/%s/%s".formatted(classId, request.getSubjectId()));
        }
    }

    @ModelAttribute(name = "userfeed", binding = false)
    public Mono<UserfeedDTO> loadUserfeed() {
        return headManagerClient.getUserfeed();
    }
}
