package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.gateway.MovementGateway;
import java.util.List;

public class ListActiveLoansUseCase {

    private final MovementGateway movementGateway;

    public ListActiveLoansUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute() {
        return movementGateway.findByTypeAndStatus(MovementType.LOAN, MovementStatus.OPEN);
    }
}
