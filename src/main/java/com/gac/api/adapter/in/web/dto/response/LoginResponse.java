package com.gac.api.adapter.in.web.dto.response;

public record LoginResponse(String accessToken, String tokenType, UserResponse user) {
}
