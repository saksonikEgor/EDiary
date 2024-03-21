package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.ScheduledCall;
import com.saksonik.headmanager.repository.ScheduledCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduledCallService {
    private final ScheduledCallRepository scheduledCallRepository;

    public List<ScheduledCall> findAll() {
        return scheduledCallRepository.findAll();
    }
}
