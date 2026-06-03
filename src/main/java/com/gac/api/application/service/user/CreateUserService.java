package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.ConflictException;
import com.gac.api.application.security.PasswordHasher;
import com.gac.api.application.repository.UserRepository;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public CreateUserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User execute(User newUser) {
        userRepository.findByEmail(newUser.getEmail()).ifPresent(u -> {
            throw new ConflictException("Email is already in use.");
        });

        userRepository.findByRegistrationNumber(newUser.getRegistrationNumber()).ifPresent(u -> {
            throw new ConflictException("Registration number is already registered.");
        });

        if (newUser.getPassword() == null || newUser.getPassword().length() < 6) {
            throw new BusinessRuleException("Password must be at least 6 characters.");
        }

        newUser.setPassword(passwordHasher.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }
}
