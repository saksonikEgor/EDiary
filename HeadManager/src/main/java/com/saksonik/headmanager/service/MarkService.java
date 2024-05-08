package com.saksonik.headmanager.service;

import com.saksonik.headmanager.dto.marks.CreateMarkRequest;
import com.saksonik.headmanager.dto.marks.UpdateMarkRequest;
import com.saksonik.headmanager.exception.notExist.MarkNotExistException;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarkService {
    private final MarkRepository markRepository;

    public Mark findById(UUID id) {
        return markRepository.findById(id)
                .orElseThrow(() -> new MarkNotExistException("Mark not found with id: " + id));
    }

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

    public List<Mark> findAllByClassIdAndSubjectAndStudyPeriod(UUID classId, Subject subject, StudyPeriod studyPeriod) {
        return markRepository.findAllByClassIdAndSubjectAndStudyPeriod(classId, subject, studyPeriod);
    }

//    public List<Mark> findAllForTeacherAndClass(User teacher, Class clazz, Subject subject, StudyPeriod studyPeriod) {
//        return markRepository.findAllByTeacherAndClassIdAndSubjectAndStudyPeriod(
//                teacher,
//                clazz.getClassId(),
//                subject,
//                studyPeriod
//        );
//    }

    @Transactional
    public Mark createMark(CreateMarkRequest request, User student, User teacher, Subject subject, MarkType markType,
                           WorkType workType, StudyPeriod studyPeriod) {
        Mark mark = new Mark();

        mark.setCreatedAt(LocalDateTime.now());
        mark.setLastModifiedAt(LocalDateTime.now());
        mark.setDate(request.date());
        mark.setDescription(request.description());
        mark.setStudent(student);
        mark.setSubject(subject);
        mark.setTeacher(teacher);
        mark.setMarkType(markType);
        mark.setWorkType(workType);
        mark.setStudyPeriod(studyPeriod);

        return markRepository.save(mark);
    }

    @Transactional
    public Mark updateMark(UUID markId, UpdateMarkRequest request, MarkType markType, WorkType workType) {
        Mark mark = findById(markId);

        mark.setDescription(request.description());
        mark.setMarkType(markType);
        mark.setWorkType(workType);

        return markRepository.save(mark);
    }

    @Transactional
    public void deleteMarkById(UUID id) {
        markRepository.delete(findById(id));
    }

    public Float calculateAvg(List<Mark> marks) {
        float sum = 0;
        float denominator = 0;

        for (Mark mark : marks) {
            MarkType markType = mark.getMarkType();
            if (markType == null) {
                continue;
            }

            WorkType workType = mark.getWorkType();
            sum += markType.getWeight() * workType.getWeight();
            denominator += workType.getWeight();
        }

        return sum / denominator;
    }
}
