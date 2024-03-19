package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.StudyPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPeriodRepository extends JpaRepository<StudyPeriod, Integer> {
}