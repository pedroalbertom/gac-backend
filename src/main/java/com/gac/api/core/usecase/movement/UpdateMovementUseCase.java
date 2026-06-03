package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.gateway.MovementGateway;

public class UpdateMovementUseCase {

    private final MovementGateway movementGateway;

    public UpdateMovementUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public Movement execute(Long id, Movement updatedData) {
        Movement existing = movementGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement record not found."));

        if (existing.getStatus() != MovementStatus.OPEN) {
            throw new RuntimeException("Completed or cancelled movements cannot be updated.");
        }

        if (updatedData.getAcademicPurpose() != null) {
            existing.setAcademicPurpose(updatedData.getAcademicPurpose());
        }
        if (updatedData.getRoom() != null) {
            existing.setRoom(updatedData.getRoom());
        }

        return movementGateway.save(existing);
    }
}
