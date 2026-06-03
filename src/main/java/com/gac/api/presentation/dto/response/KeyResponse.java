package com.gac.api.presentation.dto.response;

import com.gac.api.core.domain.ItemStatus;

public record KeyResponse(
        Long id,
        String room,
        String block,
        String assetTag,
        boolean spareKey,
        ItemStatus status,
        String reservedRegistrationNumber,
        String defectDescription) {
}
