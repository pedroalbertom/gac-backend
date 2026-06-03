package com.gac.api.presentation.mapper;

import com.gac.api.core.domain.AssetSummary;
import com.gac.api.core.domain.Pendency;
import com.gac.api.presentation.dto.response.AssetItemResponse;
import com.gac.api.presentation.dto.response.PendencyResponse;

public final class PendencyMapper {

    private PendencyMapper() {
    }

    public static PendencyResponse toResponse(Pendency pendency) {
        return new PendencyResponse(pendency.getType(), pendency.getMovementId(), pendency.getMessage());
    }

    public static AssetItemResponse toAssetResponse(AssetSummary summary) {
        return new AssetItemResponse(
                summary.assetType(),
                summary.id(),
                summary.status(),
                summary.label(),
                summary.reservedRegistrationNumber());
    }
}
