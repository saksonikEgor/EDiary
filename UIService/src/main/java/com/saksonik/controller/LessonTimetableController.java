package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.lessonTimetable.LessonTimetableDTO;
import com.saksonik.dto.userfeed.UserfeedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.saksonik.util.DateUtil.buildPeriod;

@Slf4j
@Controller
@RequestMapping("/lesson-timetable")
@RequiredArgsConstructor
public class LessonTimetableController {
    private static final DayOfWeek WEEK_START = DayOfWeek.MONDAY;
    private final HeadManagerClient headManagerClient;

    @GetMapping("/for-class/{classId}")
    public Mono<String> getTimetableByClassForWeek(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                                   @PathVariable("classId") UUID classId,
                                                   @RequestParam(value = "year", required = false) Integer year,
                                                   @RequestParam(value = "month", required = false) Integer month,
                                                   @RequestParam(value = "day", required = false) Integer day,
                                                   Model model) {
        log.info("Getting timetable for classId {}", classId);

        Integer yearRequest = year;
        Integer monthRequest = month;
        Integer dayRequest = day;

        if (year == null || day == null || month == null) {
            LocalDateTime now = LocalDateTime.now();

            yearRequest = now.getYear();
            monthRequest = now.getMonthValue();
            dayRequest = now.getDayOfMonth();
        }

        LocalDate date = LocalDate.of(yearRequest, monthRequest, dayRequest);
        LocalDate firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(WEEK_START));
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        model.addAttribute("period", buildPeriod(firstDayOfWeek, lastDayOfWeek));
        model.addAttribute("startDay", firstDayOfWeek);
        model.addAttribute("endDay", lastDayOfWeek);
        model.addAttribute("forTeacher", false);

        return headManagerClient.getLessonTimetableForClass(classId, firstDayOfWeek, lastDayOfWeek)
                .collectMap(LessonTimetableDTO::getDate, lessonTimetable -> {
                    Map<Integer, LessonTimetableDTO.LessonDTO> numberToLesson = new HashMap<>();
                    lessonTimetable.getLessons().forEach(l -> numberToLesson.put(l.getLessonNumber(), l));

                    return numberToLesson;
                })
                .doOnNext(lessonTimetable -> model.addAttribute("lessonTimetable", lessonTimetable))
                .then(headManagerClient.getClassById(classId)
                        .doOnNext(c -> model.addAttribute("class", c)))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .thenReturn("lessonTimetable/lesson-timetable");
    }

    @GetMapping("/for-teacher/{teacherId}")
    public Mono<String> getTimetableByTeacherForWeek(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                                     @PathVariable("teacherId") UUID teacherId,
                                                     @RequestParam(value = "year", required = false) Integer year,
                                                     @RequestParam(value = "month", required = false) Integer month,
                                                     @RequestParam(value = "day", required = false) Integer day,
                                                     Model model) {
        log.info("Getting timetable for teacherId {}", teacherId);

        Integer yearRequest = year;
        Integer monthRequest = month;
        Integer dayRequest = day;

        if (year == null || day == null || month == null) {
            LocalDateTime now = LocalDateTime.now();

            yearRequest = now.getYear();
            monthRequest = now.getMonthValue();
            dayRequest = now.getDayOfMonth();
        }

        LocalDate date = LocalDate.of(yearRequest, monthRequest, dayRequest);
        LocalDate firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(WEEK_START));
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        model.addAttribute("period", buildPeriod(firstDayOfWeek, lastDayOfWeek));
        model.addAttribute("startDay", firstDayOfWeek);
        model.addAttribute("endDay", lastDayOfWeek);
        model.addAttribute("forTeacher", true);

        return headManagerClient.getLessonTimetableForTeacher(teacherId, firstDayOfWeek, lastDayOfWeek)
                .collectMap(LessonTimetableDTO::getDate, lessonTimetable -> {
                    Map<Integer, LessonTimetableDTO.LessonDTO> numberToLesson = new HashMap<>();
                    lessonTimetable.getLessons().forEach(l -> numberToLesson.put(l.getLessonNumber(), l));

                    return numberToLesson;
                })
                .doOnNext(lessonTimetable -> model.addAttribute("lessonTimetable", lessonTimetable))
                .then(headManagerClient.getUserById(teacherId)
                        .doOnNext(teacher -> model.addAttribute("teacher", teacher)))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .thenReturn("lessonTimetable/lesson-timetable");
    }

    @ModelAttribute(name = "userfeed", binding = false)
    public Mono<UserfeedDTO> loadUserfeed() {
        return headManagerClient.getUserfeed();
    }
}
