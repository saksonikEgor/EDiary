package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarkService {
    private final MarkRepository markRepository;

    public List<Mark> findAll() {
        return markRepository.findAll();
    }

    public List<Mark> findAllByStudentAndStudyPeriod(User student, StudyPeriod studyPeriod) {
        return markRepository.findAllByStudentAndStudyPeriod(student, studyPeriod);
    }

    public List<Mark> findAllForStudentAndSubjectAndStudyPeriod(User student, Subject subject, StudyPeriod studyPeriod) {
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

    public Float calculateAvg(List<Mark> marks) {
        Set<WorkType> workTypes = new HashSet<>();
        float workTypeWeightSum = 0;
        float sum = 0;

        for (Mark mark : marks) {
            MarkType markType = mark.getMarkType();
            if (markType.getWeight() == null) {
                continue;
            }

            WorkType workType = mark.getWorkType();
            if (workTypes.add(workType)) {
                workTypeWeightSum += workType.getWeight();
            }

            sum += (markType.getWeight() * workType.getWeight());
        }

        return sum / workTypeWeightSum;
    }
}
