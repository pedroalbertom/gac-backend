package com.gac.api.presentation.dto.response;

import com.gac.api.core.domain.ItemStatus;

public record ProjectorResponse(
        Long id,
        String brand,
        String model,
        String serialNumber,
        String assetTag,
        ItemStatus status,
        String reservedRegistrationNumber,
        String defectDescription) {
}
