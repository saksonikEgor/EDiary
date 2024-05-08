package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.StudentDistribution;
import com.saksonik.headmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentDistributionRepository extends JpaRepository<StudentDistribution, UUID> {
    Optional<StudentDistribution> findByStudent(User student);

    List<StudentDistribution> findAllByClazz(Class c);
}