package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, UUID> {
}