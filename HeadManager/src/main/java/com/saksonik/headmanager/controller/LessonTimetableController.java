package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.LessonTimetableDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.LessonSchedule;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.LessonScheduleService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/lesson-timetable")
@RequiredArgsConstructor
public class LessonTimetableController {
    private static final DayOfWeek WEEK_START = DayOfWeek.MONDAY;
    private final UserService userService;
    private final LessonScheduleService lessonScheduleService;
    private final ClassService classService;

    //TODO  подумать нужны ли тут роли
    //TODO  добавить обработку исключения classNotFound
    //TODO  добавить обработку исключения wrongData
    @GetMapping("/{id}")
    public ResponseEntity<LessonTimetableDTO> getTimetableByClassForWeek(
            @PathVariable("id") Integer classId,
            @RequestParam("year") Integer year,
            @RequestParam("monty") Integer month,
            @RequestParam("day") Integer day
    ) {
        Class c = classService.findById(classId);

        LocalDate date = LocalDate.of(year, month, day);
        LocalDate firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(WEEK_START));
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        List<LessonSchedule> lessons = lessonScheduleService
                .findAllByClazzIsAndLessonDateBeforeAndAndLessonDateTimeAfter(
                        c, firstDayOfWeek, lastDayOfWeek
                );

        List<LessonTimetableDTO.DayDTO.LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> new LessonTimetableDTO.DayDTO.LessonDTO(
                        lesson.getScheduledCall().getLessonNumber(),
                        lesson.getClassroom().getName(),
                        lesson.getTeacher().getFullName(),
                        lesson.getSubject().getName(),
                        lesson.getScheduledCall().getStart(),
                        lesson.getScheduledCall().getEnd(),
                        lesson.getLessonDateTime().toLocalDate())
                )
                .toList();

        List<LessonTimetableDTO.DayDTO> week = buildWeekByDays(firstDayOfWeek, lastDayOfWeek);

        lessonDTOS.forEach(l -> week.stream()
                .filter(d -> d.getDate().equals(l.getDate()))
                .findFirst()
                .get()
                .addLesson(l));

        return ResponseEntity.ok(new LessonTimetableDTO(week));
    }

    //TODO  проверить что у бзера есть права на это
    //TODO  добавить обработку исключения teacherNotFound
    //TODO  добавить обработку исключения wrongData
    @GetMapping
    public ResponseEntity<LessonTimetableDTO> getTimetableByTeacherForWeek(
            @RequestHeader("User-Id") UUID teacherId,
            @RequestParam("year") Integer year,
            @RequestParam("monty") Integer month,
            @RequestParam("day") Integer day
    ) {
        User teacher = userService.findUserById(teacherId);

        LocalDate date = LocalDate.of(year, month, day);
        LocalDate firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(WEEK_START));
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        List<LessonSchedule> lessons = lessonScheduleService
                .findAllByTeacherIsAndLessonDateBeforeAndLessonDateTimeAfter(
                        teacher, firstDayOfWeek, lastDayOfWeek
                );

        List<LessonTimetableDTO.DayDTO.LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> new LessonTimetableDTO.DayDTO.LessonDTO(
                        lesson.getScheduledCall().getLessonNumber(),
                        lesson.getClassroom().getName(),
                        lesson.getTeacher().getFullName(),
                        lesson.getSubject().getName(),
                        lesson.getScheduledCall().getStart(),
                        lesson.getScheduledCall().getEnd(),
                        lesson.getLessonDateTime().toLocalDate())
                )
                .toList();

        List<LessonTimetableDTO.DayDTO> week = buildWeekByDays(firstDayOfWeek, lastDayOfWeek);

        lessonDTOS.forEach(l -> week.stream()
                .filter(d -> d.getDate().equals(l.getDate()))
                .findFirst()
                .get()
                .addLesson(l));

        return ResponseEntity.ok(new LessonTimetableDTO(week));
    }

    private List<LessonTimetableDTO.DayDTO> buildWeekByDays(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        List<LessonTimetableDTO.DayDTO> week = new ArrayList<>();
        LocalDate date = firstDayOfWeek;

        while (!date.isAfter(lastDayOfWeek)) {
            var day = new LessonTimetableDTO.DayDTO();
            day.setDate(date);
            week.add(day);
            date = date.plusDays(1);
        }

        return week;
    }
}
