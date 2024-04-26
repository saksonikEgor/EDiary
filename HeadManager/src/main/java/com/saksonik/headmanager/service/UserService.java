package com.saksonik.headmanager.service;

import com.saksonik.headmanager.exception.UserNotExistException;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllStudentsByClassId(int classId) {
        return userRepository.findAllStudentsByClassId(classId);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User not found with id: " + userId));
    }

    public List<User> findAllByIds(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }
}
