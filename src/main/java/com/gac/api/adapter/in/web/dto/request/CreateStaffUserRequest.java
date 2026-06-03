package com.gac.api.adapter.in.web.dto.request;

import com.gac.api.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateStaffUserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String registrationNumber,
        @NotBlank @Size(min = 6) String password,
        @NotNull Role role) {
}
