package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Meeting;
import com.saksonik.headmanager.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }
}
