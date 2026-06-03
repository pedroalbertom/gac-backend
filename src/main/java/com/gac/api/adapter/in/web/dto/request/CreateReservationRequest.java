package com.gac.api.adapter.in.web.dto.request;

import com.gac.api.domain.model.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull AssetType assetType, @NotNull Long assetId, @NotBlank String academicPurpose) {
}
