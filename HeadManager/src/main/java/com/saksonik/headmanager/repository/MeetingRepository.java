package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Meeting;
import com.saksonik.headmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
//    List<Meeting> findAllByClazz(Class clazz);

    @Query("select m from Meeting m where m.clazz.classroomTeacher = :classroomTeacher")
    List<Meeting> findAllByClassroomTeacher(User classroomTeacher);
}