package com.gac.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProfessorRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String registrationNumber,
        @NotBlank @Size(min = 6) String password) {
}
