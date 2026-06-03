package com.gac.api.adapter.in.web.mapper;

import com.gac.api.domain.model.Movement;
import com.gac.api.adapter.in.web.dto.response.MovementResponse;

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
                movement.getLoanedAccessories(),
                movement.getReturnedAccessories());
    }
}
