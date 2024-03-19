package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.ScheduledCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledCallRepository extends JpaRepository<ScheduledCall, Integer> {
}