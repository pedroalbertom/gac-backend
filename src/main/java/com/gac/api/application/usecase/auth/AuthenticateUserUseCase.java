package com.gac.api.application.usecase.auth;

import com.gac.api.application.port.in.auth.AuthenticateUserInputPort;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.UnauthorizedException;
import com.gac.api.application.port.out.PasswordHasher;
import com.gac.api.domain.port.UserGateway;

public class AuthenticateUserUseCase implements AuthenticateUserInputPort {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public AuthenticateUserUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    public User execute(String registrationNumber, String rawPassword) {
        User user = userGateway
                .findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new UnauthorizedException("Invalid registration number or password."));

        if (!passwordHasher.matches(rawPassword, user.getPassword())) {
            throw new UnauthorizedException("Invalid registration number or password.");
        }

        return user;
    }
}
