package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.gateway.MovementGateway;
import java.util.List;

public class ListOpenReservationsUseCase {

    private final MovementGateway movementGateway;

    public ListOpenReservationsUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute(String professorRegistrationNumber) {
        return movementGateway.findOpenByProfessorAndType(professorRegistrationNumber, MovementType.RESERVATION);
    }
}
