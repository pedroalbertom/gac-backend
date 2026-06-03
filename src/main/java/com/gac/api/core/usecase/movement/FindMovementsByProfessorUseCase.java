package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.gateway.MovementGateway;
import java.util.List;

public class FindMovementsByProfessorUseCase {

    private final MovementGateway movementGateway;

    public FindMovementsByProfessorUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute(String registrationNumber) {
        return movementGateway.findByProfessorRegistrationNumber(registrationNumber);
    }
}
