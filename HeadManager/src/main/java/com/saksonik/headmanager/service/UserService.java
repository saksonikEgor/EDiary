package com.saksonik.headmanager.service;

import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllStudentsByClassId(int classId) {
        return userRepository.findAllStudentsByClassId(classId);
    }
}
