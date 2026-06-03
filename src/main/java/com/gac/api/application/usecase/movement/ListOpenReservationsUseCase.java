package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.ListOpenReservationsInputPort;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.port.out.MovementGateway;
import java.util.List;

public class ListOpenReservationsUseCase implements ListOpenReservationsInputPort {

    private final MovementGateway movementGateway;

    public ListOpenReservationsUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Movement> execute(String professorRegistrationNumber) {
        return movementGateway.findOpenByProfessorAndType(professorRegistrationNumber, MovementType.RESERVATION);
    }
}
