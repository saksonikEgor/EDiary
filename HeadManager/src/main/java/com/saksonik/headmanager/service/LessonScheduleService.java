package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.LessonSchedule;
import com.saksonik.headmanager.repository.LessonScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonScheduleService {
    private final LessonScheduleRepository lessonScheduleRepository;

    public List<LessonSchedule> findAll() {
        return lessonScheduleRepository.findAll();
    }
}
