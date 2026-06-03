package com.gac.api.presentation.mapper;

import com.gac.api.core.domain.Movement;
import com.gac.api.presentation.dto.response.MovementResponse;

public final class MovementMapper {

    private MovementMapper() {
    }

    public static MovementResponse toResponse(Movement movement) {
        return new MovementResponse(
                movement.getId(),
                movement.getType(),
                movement.getStatus(),
                movement.getProfessorRegistrationNumber(),
                movement.getAttendantId(),
                movement.getAssetType(),
                movement.getAssetId(),
                movement.getConfirmationCode(),
                movement.getAcademicPurpose(),
                movement.getRoom(),
                movement.getDefectDescription(),
                movement.getCheckedOutAt(),
                movement.getReturnedAt(),
                movement.getCreatedAt(),
                movement.getLoanedAccessories());
    }
}
