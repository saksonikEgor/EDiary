package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.StudyPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StudyPeriodRepository extends JpaRepository<StudyPeriod, Integer> {
    @Query("select sp from StudyPeriod sp where sp.start <= :date and sp.end >= :date")
    Optional<StudyPeriod> findCurrentStudyPeriod(@Param("date") LocalDate date);

    Optional<StudyPeriod> findByName(String name);
}