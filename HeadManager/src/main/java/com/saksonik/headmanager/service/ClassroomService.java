package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.ClassroomNotExistException;
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

    public Classroom findById(Integer id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotExistException("Classroom not found with id " + id));
    }

    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    public List<Classroom> findAllByIds(List<Integer> ids) {
        return classroomRepository.findAllById(ids);
    }
}
