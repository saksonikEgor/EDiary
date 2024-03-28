package com.saksonik.authenticator.service;

import com.saksonik.authenticator.dto.AuthRequest;
import com.saksonik.authenticator.dto.AuthResponse;
import com.saksonik.authenticator.model.User;
import com.saksonik.authenticator.repository.UserRepository;
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
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(AuthRequest authRequest) {
        if (userRepository.findUserByLoginOrEmail(authRequest.login(), authRequest.email()).isPresent()) {
            throw new RuntimeException("User with same login or email is already registered");
        }

//        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
//        UserVO registeredUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);

        User user = new User();

        user.setLogin(authRequest.login());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        user.setEmail(authRequest.email());
        user.setRegistrationDate(OffsetDateTime.now());

        userRepository.save(user);

        String accessToken = jwtUtil.generate(String.valueOf(user.getUserId()), "USER", "ACCESS");
        String refreshToken = jwtUtil.generate(String.valueOf(user.getUserId()), "USER", "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }
}
