package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Integer> {
//    TODO: по classId и временому периоду получать распсиание занятий (List<LessonSchedule>)
}