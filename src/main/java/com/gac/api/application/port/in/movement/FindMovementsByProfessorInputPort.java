package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Movement;
import java.util.List;

public interface FindMovementsByProfessorInputPort {
    List<Movement> execute(String registrationNumber);
}
