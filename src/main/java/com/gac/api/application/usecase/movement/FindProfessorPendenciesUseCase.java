package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.FindProfessorPendenciesInputPort;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.Pendency;
import com.gac.api.domain.port.MovementGateway;
import java.util.List;

public class FindProfessorPendenciesUseCase implements FindProfessorPendenciesInputPort {

    private final MovementGateway movementGateway;

    public FindProfessorPendenciesUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Pendency> execute(String professorRegistrationNumber) {
        return ProfessorPendencyRules.findPendencies(professorRegistrationNumber, movementGateway);
    }
}
