package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select s from User s join s.studentDistribution sd where sd.clazz.classId = :classId")
    List<User> findAllStudentsByClassId(@Param("classId") int classId);

//    @Query("select p from User p join p.children c where c.userId = :childId")
//    List<User> findAllParentsByChildId(int childId);

}
