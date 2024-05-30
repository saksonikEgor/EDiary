package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  @Query("select s from User s join s.studentDistribution sd where sd.clazz.classId = :classId")
  List<User> findAllStudentsByClassId(@Param("classId") int classId);

  @Override
  @NotNull
  Optional<User> findById(@NotNull UUID uuid);

  List<User> findAllByStudentDistributionIsNotEmpty();
}