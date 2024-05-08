package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.notExist.StudentDistributionNotExist;
import com.saksonik.headmanager.model.StudentDistribution;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.StudentDistributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentDistributionService {
    private final StudentDistributionRepository studentDistributionRepository;

    public StudentDistribution findByStudent(User student) {
        return studentDistributionRepository.findByStudent(student)
                .orElseThrow(() -> new StudentDistributionNotExist("Student distribution for student " + student
                        + " not found"));
    }
}
