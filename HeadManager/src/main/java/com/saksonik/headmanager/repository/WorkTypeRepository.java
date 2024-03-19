package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
}