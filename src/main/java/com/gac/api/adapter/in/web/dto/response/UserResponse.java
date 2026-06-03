package com.gac.api.adapter.in.web.dto.response;

import com.gac.api.domain.model.Role;

public record UserResponse(Long id, String name, String email, String registrationNumber, Role role) {
}
