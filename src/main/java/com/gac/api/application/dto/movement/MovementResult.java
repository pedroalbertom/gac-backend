package com.gac.api.application.dto.movement;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import java.time.LocalDateTime;
import java.util.List;

/** Application output model for movement use cases (adapter maps to HTTP DTO). */
public record MovementResult(
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

    public static MovementResult from(Movement movement) {
        return new MovementResult(
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
