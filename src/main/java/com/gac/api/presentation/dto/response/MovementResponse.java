package com.gac.api.presentation.dto.response;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import java.time.LocalDateTime;
import java.util.List;

public record MovementResponse(
        Long id,
        MovementType type,
        MovementStatus status,
        String professorRegistrationNumber,
        Long attendantId,
        AssetType assetType,
        Long assetId,
        String confirmationCode,
        String academicPurpose,
        String room,
        String defectDescription,
        LocalDateTime checkedOutAt,
        LocalDateTime returnedAt,
        LocalDateTime createdAt,
        List<String> loanedAccessories,
        List<String> returnedAccessories) {
}
