package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.Role;
import com.saksonik.headmanager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
