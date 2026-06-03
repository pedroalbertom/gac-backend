package com.gac.api.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProjectorRequest(
        @NotBlank String brand, String model, String serialNumber, @NotBlank String assetTag) {
}
