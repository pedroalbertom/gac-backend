package com.gac.api.infrastructure.security;

import com.gac.api.domain.model.Role;

public record JwtUserPrincipal(Long userId, String registrationNumber, Role role) {
}
