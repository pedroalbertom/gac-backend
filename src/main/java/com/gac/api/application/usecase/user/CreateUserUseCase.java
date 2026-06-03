package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.CreateUserInputPort;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.ConflictException;
import com.gac.api.application.port.out.PasswordHasher;
import com.gac.api.application.port.out.UserGateway;

public class CreateUserUseCase implements CreateUserInputPort {

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
