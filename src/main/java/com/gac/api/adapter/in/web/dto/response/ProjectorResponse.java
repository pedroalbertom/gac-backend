package com.gac.api.adapter.in.web.dto.response;

import com.gac.api.domain.model.ItemStatus;

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
