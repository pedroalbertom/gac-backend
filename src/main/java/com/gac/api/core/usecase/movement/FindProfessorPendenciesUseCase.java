package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Pendency;
import com.gac.api.core.gateway.MovementGateway;
import java.util.List;

public class FindProfessorPendenciesUseCase {

    private final MovementGateway movementGateway;

    public FindProfessorPendenciesUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public List<Pendency> execute(String professorRegistrationNumber) {
        return ProfessorPendencyRules.findPendencies(professorRegistrationNumber, movementGateway);
    }
}
