package com.gac.api.adapter.in.web.dto.response;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.ItemStatus;

public record AssetItemResponse(
        AssetType assetType,
        Long id,
        ItemStatus status,
        String label,
        String reservedRegistrationNumber) {
}
