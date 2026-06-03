package com.gac.api.core.usecase.auth;

import com.gac.api.core.domain.User;
import com.gac.api.core.exception.UnauthorizedException;
import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.UserGateway;

public class AuthenticateUserUseCase {

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
