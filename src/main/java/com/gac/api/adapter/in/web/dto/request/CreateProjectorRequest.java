package com.gac.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectorRequest(
        @NotBlank String brand, String model, String serialNumber, @NotBlank String assetTag) {
}
