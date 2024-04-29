package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.notExist.MarkTypeNotExistException;
import com.saksonik.headmanager.model.MarkType;
import com.saksonik.headmanager.repository.MarkTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarkTypeService {
    private final MarkTypeRepository markTypeRepository;

    public MarkType findById(Integer id) {
        return markTypeRepository.findById(id)
                .orElseThrow(() -> new MarkTypeNotExistException("Mark type with id " + id + " not found"));
    }

    public List<MarkType> findAll() {
        return markTypeRepository.findAll();
    }
}
