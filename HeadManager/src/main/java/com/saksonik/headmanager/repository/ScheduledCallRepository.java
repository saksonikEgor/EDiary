package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.ScheduledCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduledCallRepository extends JpaRepository<ScheduledCall, UUID> {
}