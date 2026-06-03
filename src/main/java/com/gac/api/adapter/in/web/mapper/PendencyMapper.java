package com.gac.api.adapter.in.web.mapper;

import com.gac.api.domain.model.AssetSummary;
import com.gac.api.domain.model.Pendency;
import com.gac.api.adapter.in.web.dto.response.AssetItemResponse;
import com.gac.api.adapter.in.web.dto.response.PendencyResponse;

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
