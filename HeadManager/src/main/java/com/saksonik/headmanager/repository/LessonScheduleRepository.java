package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.LessonSchedule;
import com.saksonik.headmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, UUID> {
    List<LessonSchedule> findAllByClazzIsAndLessonDateBetween(
            Class clazz,
            LocalDate before,
            LocalDate after
    );

    List<LessonSchedule> findAllByTeacherIsAndLessonDateBetween(
            User teacher,
            LocalDate before,
            LocalDate after
    );

    List<LessonSchedule> findAllByClazzIsAndLessonDateIs(
            Class clszz,
            LocalDate date
    );
}