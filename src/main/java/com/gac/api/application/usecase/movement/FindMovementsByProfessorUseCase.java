package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.FindMovementsByProfessorInputPort;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.port.MovementGateway;
import java.util.List;

public class FindMovementsByProfessorUseCase implements FindMovementsByProfessorInputPort {

    private final MovementGateway movementGateway;

    public FindMovementsByProfessorUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute(String registrationNumber) {
        return movementGateway.findByProfessorRegistrationNumber(registrationNumber);
    }
}
