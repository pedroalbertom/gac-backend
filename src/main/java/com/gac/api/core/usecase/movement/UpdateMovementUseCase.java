package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.gateway.MovementGateway;

public class UpdateMovementUseCase {

    private final MovementGateway movementGateway;

    public UpdateMovementUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public Movement execute(Long id, Movement updatedData) {
        Movement existing = movementGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement record not found."));

        existing.setProfessorRegistrationNumber(updatedData.getProfessorRegistrationNumber());
        existing.setRoom(updatedData.getRoom());

        if (updatedData.getDateTime() != null) {
            existing.setDateTime(updatedData.getDateTime());
        }

        return movementGateway.save(existing);
    }
}
