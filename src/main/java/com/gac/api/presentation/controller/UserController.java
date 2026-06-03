package com.gac.api.presentation.controller;

import com.gac.api.core.exception.BusinessRuleException;
import com.gac.api.core.usecase.user.ChangePasswordUseCase;
import com.gac.api.core.usecase.user.CreateStaffUserUseCase;
import com.gac.api.core.usecase.user.DeleteUserUseCase;
import com.gac.api.core.usecase.user.GetUserByIdUseCase;
import com.gac.api.core.usecase.user.ListUsersUseCase;
import com.gac.api.core.usecase.user.UpdateUserUseCase;
import com.gac.api.infrastructure.security.JwtUserPrincipal;
import com.gac.api.presentation.dto.request.ChangePasswordRequest;
import com.gac.api.presentation.dto.request.CreateStaffUserRequest;
import com.gac.api.presentation.dto.request.UpdateUserRequest;
import com.gac.api.presentation.dto.response.UserResponse;
import com.gac.api.presentation.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Staff user management and profile (UC01, UC18)")
public class UserController {

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final CreateStaffUserUseCase createStaffUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(
            GetUserByIdUseCase getUserByIdUseCase,
            ChangePasswordUseCase changePasswordUseCase,
            CreateStaffUserUseCase createStaffUserUseCase,
            ListUsersUseCase listUsersUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.createStaffUserUseCase = createStaffUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateStaffUserRequest request) {
        var created = createStaffUserUseCase.execute(UserMapper.fromStaffRequest(request));
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(UserMapper.toResponse(created));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> list() {
        List<UserResponse> users =
                listUsersUseCase.execute().stream().map(UserMapper::toResponse).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toResponse(getUserByIdUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(
                UserMapper.toResponse(updateUserUseCase.execute(id, UserMapper.fromUpdateRequest(request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
