package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.MarkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkTypeRepository extends JpaRepository<MarkType, Integer> {
}