package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarkService {
    private final MarkRepository markRepository;

    public List<Mark> findAll() {
        return markRepository.findAll();
    }

    public List<Mark> findAllForStudent(User student, Subject subject, StudyPeriod studyPeriod) {
        return markRepository.findAllByStudentAndSubjectAndStudyPeriod(
                student,
                subject,
                studyPeriod
        );
    }

    public List<Mark> findAllForTeacherAndClass(User teacher, Class clazz, Subject subject, StudyPeriod studyPeriod) {
        return markRepository.findAllByTeacherAndClassIdAndSubjectAndStudyPeriod(
                teacher,
                clazz.getClassId(),
                subject,
                studyPeriod
        );
    }
}
