package com.gac.api.presentation.mapper;

import com.gac.api.core.domain.User;
import com.gac.api.presentation.dto.response.UserResponse;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(), user.getRegistrationNumber(), user.getRole());
    }
}
