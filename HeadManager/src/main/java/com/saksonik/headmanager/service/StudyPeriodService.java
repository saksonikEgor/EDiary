package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.notExist.StudyPeriodNotExistException;
import com.saksonik.headmanager.model.StudyPeriod;
import com.saksonik.headmanager.repository.StudyPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyPeriodService {
    private final StudyPeriodRepository studyPeriodRepository;

    public List<StudyPeriod> findAll() {
        return studyPeriodRepository.findAll();
    }

    public StudyPeriod findById(UUID id) {
        return studyPeriodRepository.findById(id)
                .orElseThrow(() -> new StudyPeriodNotExistException("Study period not found with id: " + id));
    }

    public StudyPeriod findCurrentStudyPeriod() {
        return studyPeriodRepository.findCurrentStudyPeriod(LocalDate.now())
                .orElseThrow(() -> new StudyPeriodNotExistException("Study period not found"));
    }

    public StudyPeriod findStudyPeriodByName(String name) {
        return studyPeriodRepository.findByName(name)
                .orElseThrow(() -> new StudyPeriodNotExistException("Study period not found with name: " + name));
    }

    public StudyPeriod findStudyPeriodByDate(LocalDate date) {
        return studyPeriodRepository.findCurrentStudyPeriod(date)
                .orElseThrow(() -> new StudyPeriodNotExistException("Study period not found for date: " + date));
    }
}
