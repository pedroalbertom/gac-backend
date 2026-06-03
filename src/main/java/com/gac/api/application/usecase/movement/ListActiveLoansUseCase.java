package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.ListActiveLoansInputPort;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.port.MovementGateway;
import java.util.List;

public class ListActiveLoansUseCase implements ListActiveLoansInputPort {

    private final MovementGateway movementGateway;

    public ListActiveLoansUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute() {
        return movementGateway.findByTypeAndStatus(MovementType.LOAN, MovementStatus.OPEN);
    }
}
