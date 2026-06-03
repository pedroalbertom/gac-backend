package com.gac.api.application.service.auth;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.UnauthorizedException;
import com.gac.api.application.security.PasswordHasher;
import com.gac.api.application.repository.UserRepository;

@Service
public class AuthenticateUserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthenticateUserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User execute(String registrationNumber, String rawPassword) {
        User user = userRepository
                .findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new UnauthorizedException("Invalid registration number or password."));

        if (!passwordHasher.matches(rawPassword, user.getPassword())) {
            throw new UnauthorizedException("Invalid registration number or password.");
        }

        return user;
    }
}
