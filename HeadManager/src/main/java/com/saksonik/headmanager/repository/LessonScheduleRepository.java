package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Integer> {
    List<LessonSchedule> findAllByClazzIsAndLessonDateBeforeAndAndLessonDateAfter(
            Class clazz,
            LocalDate before,
            LocalDate after
    );
}