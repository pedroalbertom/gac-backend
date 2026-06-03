package com.gac.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String registrationNumber, @NotBlank String password) {
}
