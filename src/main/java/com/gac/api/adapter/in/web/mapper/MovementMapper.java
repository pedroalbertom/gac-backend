package com.gac.api.adapter.in.web.mapper;

import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.domain.model.Movement;
import com.gac.api.adapter.in.web.dto.response.MovementResponse;

public final class MovementMapper {

    private MovementMapper() {
    }

    public static MovementResponse toResponse(MovementResult result) {
        return new MovementResponse(
                result.id(),
                result.type(),
                result.status(),
                result.professorRegistrationNumber(),
                result.attendantId(),
                result.assetType(),
                result.assetId(),
                result.confirmationCode(),
                result.academicPurpose(),
                result.room(),
                result.defectDescription(),
                result.checkedOutAt(),
                result.returnedAt(),
                result.createdAt(),
                result.loanedAccessories(),
                result.returnedAccessories());
    }

    public static MovementResponse toResponse(Movement movement) {
        return toResponse(MovementResult.from(movement));
    }
}
