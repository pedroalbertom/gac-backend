package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.exception.UnauthorizedException;
import com.gac.api.application.security.PasswordHasher;
import com.gac.api.application.repository.UserRepository;

@Service
public class ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public ChangePasswordService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public void execute(Long userId, String currentPassword, String newPassword) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));

        if (!passwordHasher.matches(currentPassword, user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect.");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new BusinessRuleException("New password must be at least 6 characters.");
        }

        user.setPassword(passwordHasher.encode(newPassword));
        userRepository.save(user);
    }
}
