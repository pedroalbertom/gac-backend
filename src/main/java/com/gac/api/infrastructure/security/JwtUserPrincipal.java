package com.gac.api.infrastructure.security;

import com.gac.api.core.domain.Role;

public record JwtUserPrincipal(Long userId, String registrationNumber, Role role) {
}
