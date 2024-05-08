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
import java.util.UUID;

@Repository
public interface MarkRepository extends JpaRepository<Mark, UUID> {
    List<Mark> findAllByStudentAndSubjectAndStudyPeriod(User student, Subject subject, StudyPeriod studyPeriod);

    List<Mark> findAllByStudentAndStudyPeriod(User student, StudyPeriod studyPeriod);

    @Query("select m from Mark m join m.student.studentDistribution.clazz c " +
            "where m.teacher = :teacher and c.classId = :classId and m.subject = :subject and m.studyPeriod = :studyPeriod")
    List<Mark> findAllByTeacherAndClassIdAndSubjectAndStudyPeriod(
            @Param("teacher") User teacher,
            @Param("classId") UUID classId,
            @Param("subject") Subject subject,
            @Param("studyPeriod") StudyPeriod studyPeriod
    );

    @Query("select m from Mark m join m.student.studentDistribution.clazz c " +
            "where m.teacher = :teacher and c.classId = :classId and m.studyPeriod = :studyPeriod")
    List<Mark> findAllByTeacherAndClassIdAndStudyPeriod(
            @Param("teacher") User teacher,
            @Param("classId") UUID classId,
            @Param("studyPeriod") StudyPeriod studyPeriod
    );

    @Query("select m from Mark m join m.student.studentDistribution.clazz c " +
            "where c.classId = :classId and m.studyPeriod = :studyPeriod and m.subject = :subject")
    List<Mark> findAllByClassIdAndSubjectAndStudyPeriod(
            @Param("classId") UUID classId,
            @Param("subject") Subject subject,
            @Param("studyPeriod") StudyPeriod studyPeriod
    );
}