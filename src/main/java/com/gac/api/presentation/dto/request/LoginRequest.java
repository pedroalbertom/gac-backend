package com.gac.api.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String registrationNumber, @NotBlank String password) {
}
