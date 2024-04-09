package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.LessonSchedule;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.LessonScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
        return lessonScheduleRepository.findAllByClazzIsAndLessonDateBeforeAndLessonDateTimeAfter(
                c, firstDayOfWeek, lastDayOfWeek
        );
    }

    public List<LessonSchedule> findAllByTeacherIsAndLessonDateBeforeAndLessonDateTimeAfter(
            User teacher,
            LocalDate before,
            LocalDate after
    ) {
        return lessonScheduleRepository.findAllByTeacherIsAndLessonDateBeforeAndLessonDateTimeAfter(
                teacher, before, after
        );
    }
}
