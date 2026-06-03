package com.gac.api.presentation.dto.response;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.ItemStatus;

public record AssetItemResponse(
        AssetType assetType,
        Long id,
        ItemStatus status,
        String label,
        String reservedRegistrationNumber) {
}
