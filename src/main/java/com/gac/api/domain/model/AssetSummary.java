package com.gac.api.domain.model;

public record AssetSummary(
        AssetType assetType,
        Long id,
        ItemStatus status,
        String label,
        String reservedRegistrationNumber) {
}
