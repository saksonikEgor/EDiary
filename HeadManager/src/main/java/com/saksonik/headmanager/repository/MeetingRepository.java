package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
}