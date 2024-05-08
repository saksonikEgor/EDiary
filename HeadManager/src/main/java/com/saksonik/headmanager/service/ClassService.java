package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassService {
    private final ClassRepository classRepository;

    public List<Class> findAll() {
        return classRepository.findAll();
    }

    public Class findById(UUID id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
    }

    public Class findByStudent(User student) {
        return student.getStudentDistribution().getClazz();
    }
}
