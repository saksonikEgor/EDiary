package com.saksonik.headmanager.service;

import com.saksonik.headmanager.dto.LessonTimetableRequest;
import com.saksonik.headmanager.exception.LessonScheduleIsAlreadyExistException;
import com.saksonik.headmanager.exception.WrongLessonTimetableCredentialsException;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.repository.LessonScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonScheduleService {
    private final LessonScheduleRepository lessonScheduleRepository;

    public List<LessonSchedule> findAll() {
        return lessonScheduleRepository.findAll();
    }

    public List<LessonSchedule> findAllByClazzIsAndLessonDateBeforeAndAndLessonDateTimeAfter(
            Class c,
            LocalDate firstDayOfWeek,
            LocalDate lastDayOfWeek
    ) {
        return lessonScheduleRepository.findAllByClazzIsAndLessonDateBeforeAndLessonDateAfter(
                c, firstDayOfWeek, lastDayOfWeek
        );
    }

    public List<LessonSchedule> findAllByTeacherIsAndLessonDateBeforeAndLessonDateTimeAfter(
            User teacher,
            LocalDate before,
            LocalDate after
    ) {
        return lessonScheduleRepository.findAllByTeacherIsAndLessonDateBeforeAndLessonDateAfter(
                teacher, before, after
        );
    }

    @Transactional
    public List<LessonSchedule> createTimetableForClass(Class c, LessonTimetableRequest request,
                                                        Map<UUID, User> teachers,
                                                        Map<Integer, Subject> subjects,
                                                        Map<Integer, Classroom> classrooms,
                                                        Map<Integer, ScheduledCall> scheduledCalls) {
        if (!lessonScheduleRepository.findAllByClazzIsAndLessonDateIs(c, request.getDate()).isEmpty()){
            throw new LessonScheduleIsAlreadyExistException("Lesson schedule already exists: " + request);
        }

        List<LessonSchedule> createdLessons = new ArrayList<>();
        List<LessonTimetableRequest.LessonDTO> lessonDTOs = request.getLessons();
        for (LessonTimetableRequest.LessonDTO lessonDTO : lessonDTOs) {
            User teacher = teachers.get(lessonDTO.getTeacherId());
            Subject subject = subjects.get(lessonDTO.getSubjectId());
            Classroom classroom = classrooms.get(lessonDTO.getClassRoomId());
            ScheduledCall scheduledCall = scheduledCalls.get(lessonDTO.getScheduledCallId());

            if (teacher == null || subject == null || classroom == null || scheduledCall == null) {
                throw new WrongLessonTimetableCredentialsException("Bag lesson timetable credentials " + lessonDTO);
            }

            LessonSchedule lessonSchedule = new LessonSchedule();

            lessonSchedule.setClazz(c);
            lessonSchedule.setTeacher(teacher);
            lessonSchedule.setSubject(subject);
            lessonSchedule.setClassroom(classroom);
            lessonSchedule.setScheduledCall(scheduledCall);
            lessonSchedule.setLessonDate(request.getDate());

            createdLessons.add(lessonSchedule);
        }

        return lessonScheduleRepository.saveAll(createdLessons);
    }
}
