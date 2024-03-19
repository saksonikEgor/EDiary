package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select s from User s join s.classList cl where cl.clazz.classId = :classId")
    List<User> findAllStudentsByClassId(@Param("classId") int classId);

//    @Query("select p from User p join p.children c where c.userId = :childId")
//    List<User> findAllParentsByChildId(int childId);

}
