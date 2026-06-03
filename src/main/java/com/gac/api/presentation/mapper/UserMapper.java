package com.gac.api.presentation.mapper;

import com.gac.api.domain.model.User;
import com.gac.api.presentation.dto.request.CreateProfessorRequest;
import com.gac.api.presentation.dto.request.CreateStaffUserRequest;
import com.gac.api.presentation.dto.request.UpdateUserRequest;
import com.gac.api.presentation.dto.response.UserResponse;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(), user.getRegistrationNumber(), user.getRole());
    }

    public static User fromStaffRequest(CreateStaffUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRegistrationNumber(request.registrationNumber());
        user.setPassword(request.password());
        user.setRole(request.role());
        return user;
    }

    public static User fromProfessorRequest(CreateProfessorRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRegistrationNumber(request.registrationNumber());
        user.setPassword(request.password());
        return user;
    }

    public static User fromUpdateRequest(UpdateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());
        return user;
    }
}
