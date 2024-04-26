package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.SubjectNotExistException;
import com.saksonik.headmanager.model.Subject;
import com.saksonik.headmanager.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject findById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotExistException("Subject with id " + id + " not found"));
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public List<Subject> findAllByIds(List<Integer> ids) {
        return subjectRepository.findAllById(ids);
    }
}
