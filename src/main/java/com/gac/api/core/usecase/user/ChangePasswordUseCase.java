package com.gac.api.core.usecase.user;

import com.gac.api.core.exception.BusinessRuleException;
import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.exception.UnauthorizedException;
import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.UserGateway;

public class ChangePasswordUseCase {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public ChangePasswordUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    public void execute(Long userId, String currentPassword, String newPassword) {
        var user = userGateway.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));

        if (!passwordHasher.matches(currentPassword, user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect.");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new BusinessRuleException("New password must be at least 6 characters.");
        }

        user.setPassword(passwordHasher.encode(newPassword));
        userGateway.save(user);
    }
}
