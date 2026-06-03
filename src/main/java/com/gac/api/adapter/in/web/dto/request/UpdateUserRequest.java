package com.gac.api.adapter.in.web.dto.request;

import com.gac.api.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(@NotBlank String name, @NotBlank @Email String email, @NotNull Role role) {
}
