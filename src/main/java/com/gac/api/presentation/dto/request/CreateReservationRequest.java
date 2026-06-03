package com.gac.api.presentation.dto.request;

import com.gac.api.core.domain.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull AssetType assetType, @NotNull Long assetId, @NotBlank String academicPurpose) {
}
