package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.User;
import com.gac.api.core.exception.BusinessRuleException;
import com.gac.api.core.exception.ConflictException;
import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.UserGateway;

public class CreateUserUseCase {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public CreateUserUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    public User execute(User newUser) {
        userGateway.findByEmail(newUser.getEmail()).ifPresent(u -> {
            throw new ConflictException("Email is already in use.");
        });

        userGateway.findByRegistrationNumber(newUser.getRegistrationNumber()).ifPresent(u -> {
            throw new ConflictException("Registration number is already registered.");
        });

        if (newUser.getPassword() == null || newUser.getPassword().length() < 6) {
            throw new BusinessRuleException("Password must be at least 6 characters.");
        }

        newUser.setPassword(passwordHasher.encode(newUser.getPassword()));
        return userGateway.save(newUser);
    }
}
