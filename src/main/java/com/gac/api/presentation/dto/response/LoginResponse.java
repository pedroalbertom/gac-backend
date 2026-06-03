package com.gac.api.presentation.dto.response;

public record LoginResponse(String accessToken, String tokenType, UserResponse user) {
}
