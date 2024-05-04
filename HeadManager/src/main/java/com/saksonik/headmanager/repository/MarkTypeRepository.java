package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.MarkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarkTypeRepository extends JpaRepository<MarkType, UUID> {
}