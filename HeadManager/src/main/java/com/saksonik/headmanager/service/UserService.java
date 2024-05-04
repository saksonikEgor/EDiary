package com.saksonik.headmanager.service;

import com.saksonik.headmanager.dto.profile.CreateProfileRequest;
import com.saksonik.headmanager.exception.alreadyExist.UserIsAlreadyExistException;
import com.saksonik.headmanager.exception.notExist.UserNotExistException;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public User save(CreateProfileRequest request) {
        userRepository.findById(request.userId())
                .ifPresent(user -> {
                    throw new UserIsAlreadyExistException("User with id: " + user.getUserId() + " is already exist");
                });

        User user = new User();

        user.setUserId(request.userId());
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setPatronymic(request.patronymic());

        return userRepository.save(user);
    }
}
