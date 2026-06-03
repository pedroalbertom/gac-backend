package com.gac.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateKeyRequest(
        @NotBlank String room, @NotBlank String block, String assetTag, boolean spareKey) {
}
