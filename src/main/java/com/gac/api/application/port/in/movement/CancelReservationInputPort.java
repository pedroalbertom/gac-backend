package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Movement;

public interface CancelReservationInputPort {
    Movement execute(Long reservationId, String professorRegistrationNumber);
}
