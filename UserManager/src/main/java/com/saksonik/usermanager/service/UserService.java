package com.saksonik.usermanager.service;

import com.saksonik.usermanager.dto.AuthRequest;
import com.saksonik.usermanager.model.User;
import com.saksonik.usermanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(AuthRequest authRequest) {
        User user = new User();

        user.setLogin(authRequest.login());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        user.setEmail(authRequest.email());
        user.setRegistrationDate(OffsetDateTime.now());

        userRepository.save(user);
    }
}
