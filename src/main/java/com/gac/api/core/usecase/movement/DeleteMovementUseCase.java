package com.gac.api.core.usecase.movement;

import com.gac.api.core.gateway.MovementGateway;

public class DeleteMovementUseCase {

    private final MovementGateway movementGateway;

    public DeleteMovementUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public void execute(Long id) {
        movementGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement record not found."));
        movementGateway.deleteById(id);
    }
}
