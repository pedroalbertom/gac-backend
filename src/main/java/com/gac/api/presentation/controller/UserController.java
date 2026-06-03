package com.gac.api.presentation.controller;

import com.gac.api.core.exception.BusinessRuleException;
import com.gac.api.core.usecase.user.ChangePasswordUseCase;
import com.gac.api.core.usecase.user.GetUserByIdUseCase;
import com.gac.api.infrastructure.security.JwtUserPrincipal;
import com.gac.api.presentation.dto.request.ChangePasswordRequest;
import com.gac.api.presentation.dto.response.UserResponse;
import com.gac.api.presentation.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    public UserController(GetUserByIdUseCase getUserByIdUseCase, ChangePasswordUseCase changePasswordUseCase) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal JwtUserPrincipal principal) {
        return ResponseEntity.ok(UserMapper.toResponse(getUserByIdUseCase.execute(principal.userId())));
    }

    @PatchMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ChangePasswordRequest request) {
        if (!request.newPassword().equals(request.confirmNewPassword())) {
            throw new BusinessRuleException("New password and confirmation do not match.");
        }

        changePasswordUseCase.execute(principal.userId(), request.currentPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }
}
