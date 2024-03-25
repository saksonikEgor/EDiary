package com.saksonik.usermanager.security;

import com.saksonik.usermanager.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("Wrong password for login '" + login + "'");
        }

        // TODO: add authorities
        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    //TODO: add logic
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
