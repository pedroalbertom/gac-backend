package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Movement;
import java.util.List;

public interface ListOpenReservationsInputPort {
    List<Movement> execute(String professorRegistrationNumber);
}
