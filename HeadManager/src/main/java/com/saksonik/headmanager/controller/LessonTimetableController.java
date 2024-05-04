package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.lessonTimetable.CreateLessonTimetableRequest;
import com.saksonik.headmanager.dto.lessonTimetable.LessonTimetableResponse;
import com.saksonik.headmanager.dto.lessonTimetable.UpdateLessonTimetableRequest;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/lesson-timetable")
@RequiredArgsConstructor
public class LessonTimetableController {
    private static final DayOfWeek WEEK_START = DayOfWeek.MONDAY;
    private final UserService userService;
    private final LessonScheduleService lessonScheduleService;
    private final ClassService classService;
    private final ClassroomService classroomService;
    private final SubjectService subjectService;
    private final ScheduledCallService scheduledCallService;

    //TODO  добавить post запрос на изменение расписания занятий (для админа)
    //TODO  подумать нужны ли тут роли
    //TODO  добавить обработку исключения classNotFound
    //TODO  добавить обработку исключения wrongData
    @GetMapping("/{id}")
    public ResponseEntity<LessonTimetableResponse> getTimetableByClassForWeek(
            @PathVariable("id") UUID classId,
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

        List<LessonTimetableResponse.DayDTO.LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> new LessonTimetableResponse.DayDTO.LessonDTO(
                        lesson.getScheduledCall().getLessonNumber(),
                        lesson.getClassroom().getName(),
                        lesson.getTeacher().getFullName(),
                        lesson.getSubject().getName(),
                        lesson.getScheduledCall().getStart(),
                        lesson.getScheduledCall().getEnd(),
                        lesson.getLessonDate())
                )
                .toList();

        List<LessonTimetableResponse.DayDTO> week = buildWeekByDays(firstDayOfWeek, lastDayOfWeek);

        lessonDTOS.forEach(l -> week.stream()
                .filter(d -> d.getDate().equals(l.getDate()))
                .findFirst()
                .get()
                .addLesson(l));

        return ResponseEntity.ok(new LessonTimetableResponse(week));
    }

    //TODO  проверить что у бзера есть права на это
    //TODO  добавить обработку исключения teacherNotFound
    //TODO  добавить обработку исключения wrongData
    @GetMapping
    public ResponseEntity<LessonTimetableResponse> getTimetableByTeacherForWeek(
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

        List<LessonTimetableResponse.DayDTO.LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> new LessonTimetableResponse.DayDTO.LessonDTO(
                        lesson.getScheduledCall().getLessonNumber(),
                        lesson.getClassroom().getName(),
                        lesson.getTeacher().getFullName(),
                        lesson.getSubject().getName(),
                        lesson.getScheduledCall().getStart(),
                        lesson.getScheduledCall().getEnd(),
                        lesson.getLessonDate())
                )
                .toList();

        List<LessonTimetableResponse.DayDTO> week = buildWeekByDays(firstDayOfWeek, lastDayOfWeek);

        lessonDTOS.forEach(l -> week.stream()
                .filter(d -> d.getDate().equals(l.getDate()))
                .findFirst()
                .get()
                .addLesson(l));

        return ResponseEntity.ok(new LessonTimetableResponse(week));
    }

    //TODO  добавить обработку исключений
    @PostMapping("/{id}")
    public ResponseEntity<LessonTimetableResponse> createTimetableForDay(
            @PathVariable("id") UUID classId,
            @RequestBody CreateLessonTimetableRequest request) {
        List<CreateLessonTimetableRequest.LessonDTO> lessons = request.getLessons();

        Class c = classService.findById(classId);
        Map<UUID, User> teachers = new HashMap<>();
        Map<UUID, Classroom> classrooms = new HashMap<>();
        Map<UUID, Subject> subjects = new HashMap<>();
        Map<Integer, ScheduledCall> scheduledCalls = new HashMap<>();

        userService.findAllByIds(lessons
                        .stream()
                        .map(CreateLessonTimetableRequest.LessonDTO::getTeacherId)
                        .toList())
                .forEach(u -> teachers.put(u.getUserId(), u));
        classroomService.findAllByIds(lessons
                        .stream()
                        .map(CreateLessonTimetableRequest.LessonDTO::getClassRoomId)
                        .toList())
                .forEach(cr -> classrooms.put(cr.getClassroomId(), cr));
        subjectService.findAllByIds(lessons
                        .stream()
                        .map(CreateLessonTimetableRequest.LessonDTO::getSubjectId)
                        .toList())
                .forEach(s -> subjects.put(s.getSubjectId(), s));
        scheduledCallService.findAllByIds(lessons
                        .stream()
                        .map(CreateLessonTimetableRequest.LessonDTO::getScheduledCallId)
                        .toList())
                .forEach(sc -> scheduledCalls.put(sc.getLessonNumber(), sc));

        List<LessonSchedule> createdLessons = lessonScheduleService.createTimetableForClass(
                c, request, teachers, subjects, classrooms, scheduledCalls);

        List<LessonTimetableResponse.DayDTO.LessonDTO> lessonsResponse = createdLessons.stream()
                .map(l -> new LessonTimetableResponse.DayDTO.LessonDTO(
                        l.getScheduledCall().getLessonNumber(),
                        l.getClassroom().getName(),
                        l.getTeacher().getFullName(),
                        l.getSubject().getName(),
                        l.getScheduledCall().getStart(),
                        l.getScheduledCall().getEnd(),
                        l.getLessonDate()))
                .toList();

        var day = new LessonTimetableResponse.DayDTO();
        day.setLessons(lessonsResponse);
        day.setDate(lessonsResponse.getFirst().getDate());

        return ResponseEntity.ok(new LessonTimetableResponse(List.of(day)));
    }

    @PatchMapping()
    public ResponseEntity<LessonTimetableResponse> updateTimetableForDay(@RequestBody UpdateLessonTimetableRequest request) {
        List<UpdateLessonTimetableRequest.LessonDTO> lessons = request.getLessons();

        Map<UUID, User> teachers = new HashMap<>();
        Map<UUID, Classroom> classrooms = new HashMap<>();
        Map<UUID, Subject> subjects = new HashMap<>();
        Map<Integer, ScheduledCall> scheduledCalls = new HashMap<>();

        userService.findAllByIds(lessons
                        .stream()
                        .map(UpdateLessonTimetableRequest.LessonDTO::getTeacherId)
                        .toList())
                .forEach(u -> teachers.put(u.getUserId(), u));
        classroomService.findAllByIds(lessons
                        .stream()
                        .map(UpdateLessonTimetableRequest.LessonDTO::getClassRoomId)
                        .toList())
                .forEach(cr -> classrooms.put(cr.getClassroomId(), cr));
        subjectService.findAllByIds(lessons
                        .stream()
                        .map(UpdateLessonTimetableRequest.LessonDTO::getSubjectId)
                        .toList())
                .forEach(s -> subjects.put(s.getSubjectId(), s));
        scheduledCallService.findAllByIds(lessons
                        .stream()
                        .map(UpdateLessonTimetableRequest.LessonDTO::getScheduledCallId)
                        .toList())
                .forEach(sc -> scheduledCalls.put(sc.getLessonNumber(), sc));

        List<LessonSchedule> createdLessons = lessonScheduleService.updateTimetables(
                request, teachers, subjects, classrooms, scheduledCalls);

        List<LessonTimetableResponse.DayDTO.LessonDTO> lessonsResponse = createdLessons.stream()
                .map(l -> new LessonTimetableResponse.DayDTO.LessonDTO(
                        l.getScheduledCall().getLessonNumber(),
                        l.getClassroom().getName(),
                        l.getTeacher().getFullName(),
                        l.getSubject().getName(),
                        l.getScheduledCall().getStart(),
                        l.getScheduledCall().getEnd(),
                        l.getLessonDate()))
                .toList();

        var day = new LessonTimetableResponse.DayDTO();
        day.setLessons(lessonsResponse);
        day.setDate(lessonsResponse.getFirst().getDate());

        return ResponseEntity.ok(new LessonTimetableResponse(List.of(day)));
    }

    private List<LessonTimetableResponse.DayDTO> buildWeekByDays(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        List<LessonTimetableResponse.DayDTO> week = new ArrayList<>();
        LocalDate date = firstDayOfWeek;

        while (!date.isAfter(lastDayOfWeek)) {
            var day = new LessonTimetableResponse.DayDTO();
            day.setDate(date);
            week.add(day);
            date = date.plusDays(1);
        }

        return week;
    }
}
