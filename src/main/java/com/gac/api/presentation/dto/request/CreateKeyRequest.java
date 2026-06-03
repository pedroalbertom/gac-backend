package com.gac.api.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateKeyRequest(
        @NotBlank String room, @NotBlank String block, String assetTag, boolean spareKey) {
}
