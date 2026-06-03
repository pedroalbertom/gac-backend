package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.gateway.MovementGateway;
import java.util.List;

public class ListMovementsUseCase {

    private final MovementGateway movementGateway;

    public ListMovementsUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute() {
        return movementGateway.findAll();
    }
}
