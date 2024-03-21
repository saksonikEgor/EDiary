package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Classroom;
import com.saksonik.headmanager.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }
}
