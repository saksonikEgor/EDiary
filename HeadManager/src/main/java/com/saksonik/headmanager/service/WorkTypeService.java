package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.WorkTypeNotExistException;
import com.saksonik.headmanager.model.WorkType;
import com.saksonik.headmanager.repository.WorkTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkTypeService {
    private final WorkTypeRepository workTypeRepository;

    public WorkType findById(Integer id) {
        return workTypeRepository.findById(id)
                .orElseThrow(() -> new WorkTypeNotExistException("Work type with id " + id + " not found"));
    }

    public List<WorkType> findAll() {
        return workTypeRepository.findAll();
    }
}
