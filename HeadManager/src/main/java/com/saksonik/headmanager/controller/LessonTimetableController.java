package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.lessonTimetable.LessonTimetableResponse;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.LessonSchedule;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.LessonScheduleService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/lesson-timetable")
@RequiredArgsConstructor
public class LessonTimetableController {
    private final UserService userService;
    private final LessonScheduleService lessonScheduleService;
    private final ClassService classService;

    @GetMapping("/for-class/{classId}")
    public ResponseEntity<List<LessonTimetableResponse>> getTimetableByClassForWeek(@PathVariable("classId") UUID classId,
                                                                                    @RequestParam("start") LocalDate startDate,
                                                                                    @RequestParam("end") LocalDate endDate) {
        log.info("Get timetable by class {} for week: {} - {}", classId, startDate, endDate);

        Class c = classService.findById(classId);

        List<LessonSchedule> lessons = lessonScheduleService
                .findAllByClazzIsAndLessonDateBetween(c, startDate, endDate);

        lessons.forEach(l -> l.getTeacher().buildFullName());

        List<LessonTimetableResponse.LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> new LessonTimetableResponse.LessonDTO(
                        lesson.getScheduledCall().getLessonNumber(),
                        lesson.getClassroom().getName(),
                        lesson.getClazz().getName(),
                        lesson.getTeacher().getFullName(),
                        lesson.getSubject().getName(),
                        lesson.getScheduledCall().getStart(),
                        lesson.getScheduledCall().getEnd(),
                        lesson.getLessonDate())
                )
                .toList();

        List<LessonTimetableResponse> period = buildPeriod(startDate, endDate);

        lessonDTOS.forEach(l -> period.stream()
                .filter(d -> d.getDate().equals(l.getDate()))
                .findFirst()
                .get()
                .addLesson(l));

        return ResponseEntity.ok(period);
    }

    @GetMapping("/for-teacher/{teacherId}")
    public ResponseEntity<List<LessonTimetableResponse>> getTimetableByTeacherForWeek(
            @PathVariable("teacherId") UUID teacherId,
            @RequestParam("start") LocalDate startDate,
            @RequestParam("end") LocalDate endDate
    ) {
        log.info("Get timetable by teacher {} for week: {} - {}", teacherId, startDate, endDate);

        User teacher = userService.findUserById(teacherId);

        List<LessonSchedule> lessons = lessonScheduleService
                .findAllByTeacherIsAndLessonDateBetween(teacher, startDate, endDate);

        lessons.forEach(l -> l.getTeacher().buildFullName());

        List<LessonTimetableResponse.LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> new LessonTimetableResponse.LessonDTO(
                        lesson.getScheduledCall().getLessonNumber(),
                        lesson.getClassroom().getName(),
                        lesson.getClazz().getName(),
                        lesson.getTeacher().getFullName(),
                        lesson.getSubject().getName(),
                        lesson.getScheduledCall().getStart(),
                        lesson.getScheduledCall().getEnd(),
                        lesson.getLessonDate())
                )
                .toList();

        List<LessonTimetableResponse> period = buildPeriod(startDate, endDate);

        lessonDTOS.forEach(l -> period.stream()
                .filter(d -> d.getDate().equals(l.getDate()))
                .findFirst()
                .get()
                .addLesson(l));

        return ResponseEntity.ok(period);
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<List<LessonTimetableResponse>> createTimetableForDay(
//            @PathVariable("id") UUID classId,
//            @RequestBody CreateLessonTimetableRequest request) {
//        List<CreateLessonTimetableRequest.LessonDTO> lessons = request.getLessons();
//
//        Class c = classService.findById(classId);
//        Map<UUID, User> teachers = new HashMap<>();
//        Map<UUID, Classroom> classrooms = new HashMap<>();
//        Map<UUID, Subject> subjects = new HashMap<>();
//        Map<Integer, ScheduledCall> scheduledCalls = new HashMap<>();
//
//        userService.findAllByIds(lessons
//                        .stream()
//                        .map(CreateLessonTimetableRequest.LessonDTO::getTeacherId)
//                        .toList())
//                .forEach(u -> teachers.put(u.getUserId(), u));
//        classroomService.findAllByIds(lessons
//                        .stream()
//                        .map(CreateLessonTimetableRequest.LessonDTO::getClassRoomId)
//                        .toList())
//                .forEach(cr -> classrooms.put(cr.getClassroomId(), cr));
//        subjectService.findAllByIds(lessons
//                        .stream()
//                        .map(CreateLessonTimetableRequest.LessonDTO::getSubjectId)
//                        .toList())
//                .forEach(s -> subjects.put(s.getSubjectId(), s));
//        scheduledCallService.findAllByIds(lessons
//                        .stream()
//                        .map(CreateLessonTimetableRequest.LessonDTO::getScheduledCallId)
//                        .toList())
//                .forEach(sc -> scheduledCalls.put(sc.getLessonNumber(), sc));
//
//        List<LessonSchedule> createdLessons = lessonScheduleService.createTimetableForClass(
//                c, request, teachers, subjects, classrooms, scheduledCalls);
//
//        List<LessonTimetableResponse.LessonDTO> lessonsResponse = createdLessons.stream()
//                .map(l -> new LessonTimetableResponse.LessonDTO(
//                        l.getScheduledCall().getLessonNumber(),
//                        l.getClassroom().getName(),
//                        l.getTeacher().getFullName(),
//                        l.getSubject().getName(),
//                        l.getScheduledCall().getStart(),
//                        l.getScheduledCall().getEnd(),
//                        l.getLessonDate()))
//                .toList();
//
//        var day = new LessonTimetableResponse.DayDTO();
//        day.setLessons(lessonsResponse);
//        day.setDate(lessonsResponse.getFirst().getDate());
//
//        return ResponseEntity.ok(new LessonTimetableResponse(List.of(day)));
//    }
//
//    @PatchMapping()
//    public ResponseEntity<LessonTimetableResponse> updateTimetableForDay(
//            @RequestBody UpdateLessonTimetableRequest request) {
//        List<UpdateLessonTimetableRequest.LessonDTO> lessons = request.getLessons();
//
//        Map<UUID, User> teachers = new HashMap<>();
//        Map<UUID, Classroom> classrooms = new HashMap<>();
//        Map<UUID, Subject> subjects = new HashMap<>();
//        Map<Integer, ScheduledCall> scheduledCalls = new HashMap<>();
//
//        userService.findAllByIds(lessons
//                        .stream()
//                        .map(UpdateLessonTimetableRequest.LessonDTO::getTeacherId)
//                        .toList())
//                .forEach(u -> teachers.put(u.getUserId(), u));
//        classroomService.findAllByIds(lessons
//                        .stream()
//                        .map(UpdateLessonTimetableRequest.LessonDTO::getClassRoomId)
//                        .toList())
//                .forEach(cr -> classrooms.put(cr.getClassroomId(), cr));
//        subjectService.findAllByIds(lessons
//                        .stream()
//                        .map(UpdateLessonTimetableRequest.LessonDTO::getSubjectId)
//                        .toList())
//                .forEach(s -> subjects.put(s.getSubjectId(), s));
//        scheduledCallService.findAllByIds(lessons
//                        .stream()
//                        .map(UpdateLessonTimetableRequest.LessonDTO::getScheduledCallId)
//                        .toList())
//                .forEach(sc -> scheduledCalls.put(sc.getLessonNumber(), sc));
//
//        List<LessonSchedule> createdLessons = lessonScheduleService.updateTimetables(
//                request, teachers, subjects, classrooms, scheduledCalls);
//
//        List<LessonTimetableResponse.DayDTO.LessonDTO> lessonsResponse = createdLessons.stream()
//                .map(l -> new LessonTimetableResponse.DayDTO.LessonDTO(
//                        l.getScheduledCall().getLessonNumber(),
//                        l.getClassroom().getName(),
//                        l.getTeacher().getFullName(),
//                        l.getSubject().getName(),
//                        l.getScheduledCall().getStart(),
//                        l.getScheduledCall().getEnd(),
//                        l.getLessonDate()))
//                .toList();
//
//        var day = new LessonTimetableResponse.DayDTO();
//        day.setLessons(lessonsResponse);
//        day.setDate(lessonsResponse.getFirst().getDate());
//
//        return ResponseEntity.ok(new LessonTimetableResponse(List.of(day)));
//    }

    private List<LessonTimetableResponse> buildPeriod(LocalDate firstDay, LocalDate lastDay) {
        List<LessonTimetableResponse> period = new ArrayList<>();
        LocalDate date = firstDay;

        while (!date.isAfter(lastDay)) {
            var timetable = new LessonTimetableResponse();

            timetable.setDate(date);
            period.add(timetable);
            date = date.plusDays(1);
        }

        return period;
    }
}
