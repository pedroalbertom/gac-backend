package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.UserGateway;

public class CreateUserUseCase {

    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User newUser) {
        userGateway.findByEmail(newUser.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email is already in use.");
                });

        userGateway.findByRegistrationNumber(newUser.getRegistrationNumber())
                .ifPresent(u -> {
                    throw new RuntimeException("Registration number is already registered.");
                });

        if (newUser.getPassword() == null || newUser.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters.");
        }

        return userGateway.save(newUser);
    }
}
