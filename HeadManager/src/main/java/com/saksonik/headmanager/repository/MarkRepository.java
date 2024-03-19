package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Mark;
import com.saksonik.headmanager.model.StudyPeriod;
import com.saksonik.headmanager.model.Subject;
import com.saksonik.headmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {
    List<Mark> findAllByStudentAndSubjectAndStudyPeriod(User student, Subject subject, StudyPeriod studyPeriod);

    @Query("select m from Mark m join m.student.classList.clazz c " +
            "where m.teacher = :teacher and c.classId = :classId and m.subject = :subject and m.studyPeriod = :studyPeriod")
    List<Mark> findAllByTeacherAndClassIdAndSubjectAndStudyPeriod(
            @Param("teacher") User teacher,
            @Param("classId") int classId,
            @Param("subject") Subject subject,
            @Param("studyPeriod") StudyPeriod studyPeriod
    );
}