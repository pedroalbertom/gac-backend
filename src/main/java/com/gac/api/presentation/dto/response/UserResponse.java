package com.gac.api.presentation.dto.response;

import com.gac.api.core.domain.Role;

public record UserResponse(Long id, String name, String email, String registrationNumber, Role role) {
}
