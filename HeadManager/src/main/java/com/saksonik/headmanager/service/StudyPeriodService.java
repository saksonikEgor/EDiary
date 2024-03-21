package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.StudyPeriod;
import com.saksonik.headmanager.repository.StudyPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyPeriodService {
    private final StudyPeriodRepository studyPeriodRepository;

    public List<StudyPeriod> findAll() {
        return studyPeriodRepository.findAll();
    }
}
