package com.gac.api.core.domain;

public record AssetSummary(
        AssetType assetType,
        Long id,
        ItemStatus status,
        String label,
        String reservedRegistrationNumber) {
}
